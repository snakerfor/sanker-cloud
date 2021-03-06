package com.snaker.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.snaker.common.core.domain.R;
import com.snaker.common.enums.RedissonLockType;
import com.snaker.common.enums.SysProcessStatusEnum;
import com.snaker.common.enums.SysProcessTypeEnum;
import com.snaker.common.pojo.entity.SysProcess;
import com.snaker.common.pojo.entity.TwoTuple;
import com.snaker.common.redis.util.RedisUtils;
import com.snaker.system.domain.SysDept;
import com.snaker.system.domain.SysPost;
import com.snaker.system.domain.SysUser;
import com.snaker.system.domain.SysUserPost;
import com.snaker.system.feign.RemoteOaWebService;
import com.snaker.system.mapper.SysDeptMapper;
import com.snaker.system.mapper.SysUserMapper;
import com.snaker.system.service.IOAWebService;
import com.snaker.system.service.ISysDeptService;
import com.snaker.system.service.ISysProcessService;
import com.snaker.system.util.PasswordUtil;
import com.snaker.system.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OAWebServiceImpl implements IOAWebService {

    @Autowired
    @Lazy
    RedissonUtils redissonUtils;

    private static final String EXIST_FLAG = "0";

    private static final String DEL_FLAG = "2";

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    @Lazy
    private ISysDeptService iSysDeptService;

    @Autowired
    @Lazy
    private ISysProcessService iSysProcessService;

    @Autowired
    @Qualifier("masterDataSource")
    private DataSource dataSource;

    @Autowired
    @Lazy
    private RedisUtils redis;

    @Autowired
    @Lazy
    private RemoteOaWebService remoteOaWebService;

    @Override
    public TwoTuple<SysProcess, Boolean> initSyncDeptAndUserProcess(Boolean scheduleFlag) throws Exception {
        SysProcess sysProcess = null;
        RLock lock = redissonUtils.getLock(RedissonLockType.SYNC_DEPT_USER_LOCK);
        try {
            boolean b = lock.tryLock(1, TimeUnit.MINUTES);

            String processIdStr = redis.get(SysProcessTypeEnum.SYNC_DEPT_USER.name());
            if (StringUtils.isNotBlank(processIdStr)) {
                sysProcess = iSysProcessService.getSysProcessById(Long.parseLong(processIdStr));
                log.info("[??????????????????????????????????????????:{}]", sysProcess);
                return new TwoTuple(sysProcess, false);
            }
            String sysProcessData = scheduleFlag ? "??????" : "??????";
            sysProcess = iSysProcessService.addSysProcess(SysProcessTypeEnum.SYNC_DEPT_USER, SysProcessStatusEnum.DOING, sysProcessData);
            log.info("[????????????????????????????????????:{}]", sysProcess);

            //??????3??????
            redis.set(SysProcessTypeEnum.SYNC_DEPT_USER.name(), sysProcess.getId() + "", 60 * 3);
            return new TwoTuple(sysProcess, true);
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }


    @Override
    @Async("threadPoolTaskExecutor")
    public void asyncSyncDeptAndUser(SysProcess sysProcess) {
        try {
            syncDeptAndUser();
            sysProcess.setProcessStatus(SysProcessStatusEnum.SUCCESS);
            log.info("[???????????????????????????]");
        } catch (Throwable e) {
            log.error("[???????????????????????????]", e);
            sysProcess.setProcessStatus(SysProcessStatusEnum.FAIL);
            sysProcess.appendProcessLog(ExceptionUtils.getStackTrace(e));
        } finally {
            iSysProcessService.updateSysProcess(sysProcess);
            redis.delete(SysProcessTypeEnum.SYNC_DEPT_USER.name());
        }
    }

    private void syncDeptAndUser() throws Throwable {
        final String orgReturnOrgType = "[{\"type\":\"org\"}]";
        final String deptReturnOrgType = "[{\"type\":\"dept\"}]";
        final String personReturnOrgType = "[{\"type\":\"person\"}]";
        final String postReturnOrgType = "[{\"type\":\"post\"}]";

        List<SysUserPost> userPosts = new ArrayList<>(4000);

        List<SysDept> sysDeptsOA = new ArrayList<>(500);
        sysDeptsOA.addAll(getUserDeptAllInfo(orgReturnOrgType, SysDept.class));
        sysDeptsOA.addAll(getUserDeptAllInfo(deptReturnOrgType, SysDept.class));

        List<SysUser> sysUsersOA = getUserDeptAllInfo(personReturnOrgType, SysUser.class);
        //?????????loginName?????????????????????????????????????????????????????????
        sysUsersOA = sysUsersOA.stream().filter(item -> StringUtils.isNotBlank(item.getLoginName())).collect(Collectors.toList());

        List<SysPost> sysPostsOA = getUserDeptAllInfo(postReturnOrgType, SysPost.class);

        //??????????????????
        handleDepts(sysDeptsOA);

        //??????????????????
        handleUsers(sysDeptsOA, sysUsersOA);

        //??????????????????
        handlePost(sysPostsOA);

        //???????????????????????????????????????????????????
        sysPostsOA = handleData(sysDeptsOA, sysUsersOA, sysPostsOA, userPosts);

        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);

            //1???????????????(?????????????????????insert?????????)???
            // 2???????????????(1??????????????????oaUserId?????????????????????2???????????????)
            //3???????????????????????????????????????insert????????????
            //4?????????????????????????????????????????????????????????insert????????????
            iSysDeptService.batchSaveDeptsAndUsers(sysDeptsOA, sysUsersOA, sysPostsOA, userPosts, connection);

            connection.commit();
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    private List<SysPost> handleData(List<SysDept> sysDeptsOA, List<SysUser> sysUsersOA, List<SysPost> sysPostsOA, List<SysUserPost> userPosts) {
        //??????????????????leaderId??????
        sysDeptsOA.stream().forEach(item -> {
            SysUser leaderForDept = getLeaderForDept(item.getLeaderPost(), sysUsersOA);
            if (leaderForDept != null) {
                item.setLeader(leaderForDept.getUserName());
                item.setLeaderId(leaderForDept.getUserId());
            }
        });

        //?????????????????????????????????(??????????????????\??????????????????????????????)
        Set<String> postSet = new HashSet<>(2000);
        postSet.addAll(sysDeptsOA.stream().map(item -> item.getLeaderPost()).collect(Collectors.toSet()));
        postSet.addAll(sysDeptsOA.stream().map(item -> item.getSuperLeaderPost()).collect(Collectors.toSet()));
        sysUsersOA.stream().forEach(item -> {
            String[] postIds = item.getPostIds();
            if (ArrayUtils.isNotEmpty(postIds)) {
                Set<String> userPostSet = Arrays.stream(postIds).collect(Collectors.toSet());
                postSet.addAll(userPostSet);

                Long userId = item.getUserId();
                userPostSet.stream().forEach(postItem -> userPosts.add(new SysUserPost(userId, postItem)));
            }
        });

        sysPostsOA = sysPostsOA.stream().filter(item -> StringUtils.isNotBlank(item.getPostId()) && postSet.contains(item.getPostId())).collect(Collectors.toList());

        return sysPostsOA;
    }

    private void handlePost(List<SysPost> sysPostsOA) {
        //??????????????????
        sysPostsOA.stream().forEach(item -> item.setDelFlag(item.getOaIsAvailable() != null && item.getOaIsAvailable() ? EXIST_FLAG : DEL_FLAG));
    }

    private void handleUsers(List<SysDept> sysDeptsOA, List<SysUser> sysUsersOA) throws SQLException {
        Date nowTime = new Date();

        int maxUserId = sysUserMapper.selectMaxUserId();

        List<SysUser> sysUsersDB = sysUserMapper.selectAllUserFromOA();

        Map<String, SysUser> sysUsersKeyMapDB = sysUsersDB.stream().collect(Collectors.toMap(item -> item.getOaUserId(), item -> item));

        //?????????????????? sex ??????????????????0??? 1??? 2?????????;   OA??????:"M"???"???"  "F"???"???"
        sysUsersOA.stream().forEach(item -> {
            String sex = "2";
            String sexOri = item.getSex();
            if ("M".equalsIgnoreCase(sexOri) || "???".equals(sexOri)) {
                sex = "0";
            } else if ("F".equalsIgnoreCase(sexOri) || "???".equals(sexOri)) {
                sex = "1";
            }
            item.setSex(sex);
        });

        //???OA????????????????????????????????????
        sysUsersOA.stream().forEach(item -> {
            Boolean oaIsAvailable = item.getOaIsAvailable();
            //oaParentId????????????????????????
            if (oaIsAvailable == null || !oaIsAvailable || StringUtils.isBlank(item.getOaParentId())) {
                item.setDelFlag(DEL_FLAG);
            } else {
                item.setDelFlag(EXIST_FLAG);
            }
        });

        //?????????????????????userId??????????????????user???????????????????????????
        for (int i = 0; i < sysUsersOA.size(); i++) {
            SysUser itemOA = sysUsersOA.get(i);
            String oaUserId = itemOA.getOaUserId();
            SysUser itemDB = sysUsersKeyMapDB.get(oaUserId);
            if (itemDB == null) {
                itemOA.setUserId(Long.parseLong((++maxUserId) + ""));
                String loginName = itemOA.getLoginName();
                itemOA.setSalt(loginName);
                //????????????????????????loginName
                itemOA.setPassword(PasswordUtil.encryptPassword(loginName, loginName, loginName));

                //?????????????????? ???0?????? 1?????????
                itemOA.setStatus("0");

                itemOA.setCreateTime(nowTime);
                itemOA.setUpdateTime(nowTime);
            } else {
                //login_name???user_name???email???phonenumber???OA??????
                itemOA.setUserId(itemDB.getUserId());
                itemOA.setAvatar(itemDB.getAvatar());
                itemOA.setPassword(itemDB.getPassword());
                itemOA.setSalt(itemDB.getSalt());
                itemOA.setStatus(itemDB.getStatus());
                itemOA.setLoginIp(itemDB.getLoginIp());
                itemOA.setLoginDate(itemDB.getLoginDate());
                itemOA.setCreateBy(itemDB.getCreateBy());
                itemOA.setCreateTime(itemDB.getCreateTime());
                itemOA.setUpdateTime(nowTime);
                itemOA.setRemark(itemDB.getRemark());
            }
        }

        //??????????????????id
        Map<String, SysDept> sysDeptsKeyMapOA = sysDeptsOA.stream().collect(Collectors.toMap(item -> item.getOaId(), item -> item));
        sysUsersOA.stream().forEach(item -> {
            if (EXIST_FLAG.equalsIgnoreCase(item.getDelFlag())) {
                try {
                    String oaParentId = item.getOaParentId();
                    SysDept sysDept = sysDeptsKeyMapOA.get(oaParentId);
                    Long deptId = sysDept.getDeptId();
                    item.setDeptId(deptId);
                } catch (NullPointerException e) {
                    log.info("???item.toString()???" + item.toString());
                    throw e;
                }
            }
        });
    }

    private SysUser getLeaderForDept(String post, List<SysUser> sysUsers) {
        if (StringUtils.isNotBlank(post) && CollectionUtils.isNotEmpty(sysUsers)) {
            for (SysUser sysUser : sysUsers) {
                if (ArrayUtils.contains(sysUser.getPostIds(), post)) {
                    return sysUser;
                }
            }
        }
        return null;
    }

    private void handleDepts(List<SysDept> sysDeptsOA) {
        final String LING_STR = "0";
        final Long LING_LONG = 0L;

        Date nowTime = new Date();
        int maxDeptId = sysDeptMapper.selectMaxDeptId();

        List<SysDept> sysDeptsDB = sysDeptMapper.selectAllDept();
        Map<String, SysDept> sysDeptsKeyMapDB = sysDeptsDB.stream().collect(Collectors.toMap(item -> item.getOaId(), item -> item));

        for (SysDept itemOA : sysDeptsOA) {
            String oaId = itemOA.getOaId();
            if (!sysDeptsKeyMapDB.containsKey(oaId)) {
                //?????????????????????????????????????????????deptId
                itemOA.setDeptId(Long.parseLong((++maxDeptId) + ""));
                itemOA.setStatus(LING_STR);
                itemOA.setCreateTime(nowTime);
                itemOA.setUpdateTime(nowTime);
            } else {
                //?????????????????????????????????????????????????????????
                SysDept itemDB = sysDeptsKeyMapDB.get(oaId);
                itemOA.setDeptId(itemDB.getDeptId());
                itemOA.setOrderNum(itemDB.getOrderNum());
                itemOA.setPhone(itemDB.getPhone());
                itemOA.setEmail(itemDB.getEmail());
                itemOA.setStatus(itemDB.getStatus());
                itemOA.setCreateBy(itemDB.getCreateBy());
                itemOA.setCreateTime(itemDB.getCreateTime());
                itemOA.setUpdateTime(nowTime);
            }
        }

        sysDeptsOA.stream().forEach(itemOA -> {
            //???????????????OA????????????
            Boolean oaIsAvailable = itemOA.getOaIsAvailable();
            if (!oaIsAvailable) {
                //????????????
                itemOA.setParentId(LING_LONG);
                itemOA.setAncestors(LING_STR);
                itemOA.setDelFlag(DEL_FLAG);
            } else {
                itemOA.setDelFlag(EXIST_FLAG);
            }
        });

        Map<String, SysDept> sysDeptsIdKeyMapOA = sysDeptsOA.stream().collect(Collectors.toMap(item -> item.getOaId(), item -> item));

        //????????????id
        Long topDeptId = 0L;

        //???????????????,????????????id
        for (int i = 0; i < sysDeptsOA.size(); i++) {
            SysDept itemOA = sysDeptsOA.get(i);
            String delFlag = itemOA.getDelFlag();
            if (!delFlag.equals(DEL_FLAG)) {
                String oaParentId = itemOA.getOaParentId();
                if (StringUtils.isBlank(oaParentId)) {
                    //????????? ??????????????????
                    itemOA.setParentId(LING_LONG);
                    itemOA.setAncestors(LING_STR);

                    topDeptId = itemOA.getDeptId();
                } else {
                    Long parentId = sysDeptsIdKeyMapOA.get(oaParentId).getDeptId();
                    itemOA.setParentId(parentId);
                }
            }
        }

        //???????????????,????????????id
        for (int i = 0; i < sysDeptsOA.size(); i++) {
            SysDept itemOA = sysDeptsOA.get(i);
            if (!DEL_FLAG.equals(itemOA.getDelFlag())) {
                String oaParentId = itemOA.getOaParentId();
                if (StringUtils.isNotBlank(oaParentId)) {
                    itemOA.setAncestors(getParentDeptAncestors(topDeptId, oaParentId, sysDeptsIdKeyMapOA));
                }
            }
        }

    }


    private String getParentDeptAncestors(Long topDeptId, String oaParentId, Map<String, SysDept> sysDeptsIdKeyMapOA) {
        SysDept parentDept = sysDeptsIdKeyMapOA.get(oaParentId);
        Long parentDeptId = parentDept.getDeptId();
        if (topDeptId.longValue() == parentDeptId.longValue()) {
            return new StringBuilder().append("0,").append(parentDeptId).toString();
        }

        String oaParentParentId = parentDept.getOaParentId();
        String parentDeptAncestors = getParentDeptAncestors(topDeptId, oaParentParentId, sysDeptsIdKeyMapOA);
        return new StringBuilder().append(parentDeptAncestors).append(",").append(parentDeptId).toString();
    }


    private <T> List<T> getUserDeptAllInfo(String returnOrgType, Class<T> tClass) throws Throwable {
        R r = remoteOaWebService.getDataByReturnOrgType(returnOrgType);
        if (!r.judgeSuccess()) {
            throw new RuntimeException(r.getMsg());
        }
        String message = r.getData(String.class);
        return JSON.parseArray(message, tClass);
    }


}
