package com.snaker.system.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.snaker.common.annotation.DataScope;
import com.snaker.common.annotation.RichUserNameByUserId;
import com.snaker.common.constant.UserConstants;
import com.snaker.common.core.domain.BaseEntity;
import com.snaker.common.core.domain.R;
import com.snaker.common.core.text.Convert;
import com.snaker.common.exception.BusinessException;
import com.snaker.common.redis.util.RedisUtils;
import com.snaker.common.utils.StringUtils;
import com.snaker.common.utils.bean.BeanUtils;
import com.snaker.common.utils.poi.ExcelUtil;
import com.snaker.common.utils.reflect.ReflectUtils;
import com.snaker.common.utils.security.Md5Utils;
import com.snaker.system.domain.SysRole;
import com.snaker.system.domain.SysUser;
import com.snaker.system.domain.SysUserRole;
import com.snaker.system.mapper.SysRoleMapper;
import com.snaker.system.mapper.SysUserMapper;
import com.snaker.system.mapper.SysUserRoleMapper;
import com.snaker.system.service.ISysConfigService;
import com.snaker.system.service.ISysFileService;
import com.snaker.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author sfd
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysFileService iSysFileService;

    @Autowired
    private RedisUtils redisUtils;

    private static final String template = "template/用户批量导入模板.xlsx";

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        List<SysUser> sysUsers = userMapper.selectUserList(user);
        return sysUsers;
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Long>
     * @Description 用户映射关系
     * @Param []
     * @Author snaker
     * @Date 2021/1/27 17:00
     **/
    @Override
    public Map<String, String> selectUserNameToId() {
        SysUser user = new SysUser();
        List<SysUser> sysUsers = userMapper.selectUserList(user);
        Map<String, String> stringLongHashMap = new HashMap<String, String>();
        for (SysUser sysUser : sysUsers) {
            stringLongHashMap.put(sysUser.getUserName(), String.valueOf(sysUser.getUserId()));
        }
        return stringLongHashMap;
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByLoginName(String userName) {
        SysUser sysUser = userMapper.selectUserByLoginName(userName);
        return sysUser;
    }

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws BusinessException {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            if (SysUser.isAdmin(userId)) {
                throw new BusinessException("不允许删除超级管理员用户");
            }
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    @Override
    public int insertSysUserList(List<SysUser> users){
        return userMapper.insertSysUserList(users);
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(SysUser user) {
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        List<Long> roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }


    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<SysRole> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }


    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(Md5Utils.hash(user.getLoginName() + password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int changeStatus(SysUser user) {
        if (SysUser.isAdmin(user.getUserId())) {
            throw new BusinessException("不允许修改超级管理员用户");
        }
        return userMapper.updateUser(user);
    }

    /* (non-Javadoc)
     * @see com.snaker.system.service.ISysUserService#selectUserHasRole(java.lang.Long)
     */
    @Override
    public Set<Long> selectUserIdsHasRoles(Long[] roleIds) {
        return ArrayUtil.isNotEmpty(roleIds) ? userMapper.selectUserIdsHasRoles(roleIds) : null;
    }

    /* (non-Javadoc)
     * @see com.snaker.system.service.ISysUserService#selectUserInDept(java.lang.Long)
     */
    @Override
    public Set<Long> selectUserIdsInDepts(Long[] deptIds) {
        return ArrayUtil.isNotEmpty(deptIds) ? userMapper.selectUserIdsInDepts(deptIds) : null;
    }

    @Override
    public void richUserName(List<? extends BaseEntity> baseEntities) {
        if (CollectionUtils.isEmpty(baseEntities)) {
            return;
        }
        Set<String> ids = new HashSet<>();
        Set<String> createByIds = baseEntities.stream().map(item -> item.getCreateBy()).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(createByIds)) {
            ids.addAll(createByIds);
        }
        Set<String> updateByIds = baseEntities.stream().map(item -> item.getUpdateBy()).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(updateByIds)) {
            ids.addAll(updateByIds);
        }

        ids = ids.stream().filter(item -> org.apache.commons.lang3.StringUtils.isNotBlank(item)).collect(Collectors.toSet());

        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        Set<Long> queryLongIds = new HashSet<>();
        ids.stream().forEach(item -> {
            queryLongIds.add(Long.parseLong(item));
        });

        SysUser querySysUser = new SysUser();
        querySysUser.setQueryLongIds(queryLongIds);
        List<SysUser> sysUsersDB = userMapper.selectUserList(querySysUser);
        Map<String, String> keyMap = sysUsersDB.stream().collect(Collectors.toMap(item -> item.getUserId() + "", item -> item.getUserName()));

        baseEntities.forEach(item -> {
            String createBy = item.getCreateBy();
            String updateBy = item.getUpdateBy();
            if (keyMap.containsKey(createBy)) {
                item.setCreateByUserName(keyMap.get(createBy));
            }
            if (keyMap.containsKey(updateBy)) {
                item.setUpdateByUserName(keyMap.get(updateBy));
            }
        });
    }


    @Override
    public List<SysUser> selectSysUserByLoginNames(Set<String> loginNames) {
        if (CollectionUtils.isEmpty(loginNames)) {
            return new ArrayList<>();
        }
        SysUser queryUser = new SysUser();
        queryUser.setLoginNames(loginNames);
        return userMapper.selectUserList(queryUser);
    }

    @Override
    public List<SysUser> selectSysUserByUserIds(Set<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        Set<Long> userIdsLong = userIds.stream().map(item -> Long.parseLong(item)).collect(Collectors.toSet());
        SysUser queryUser = new SysUser();
        queryUser.setQueryLongIds(userIdsLong);
        return userMapper.selectUserList(queryUser);
    }

    @Override
    public void richUserNameByUserId(List list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Set<String> userIdsStr = new HashSet<>();
        for (Object object : list) {
            Field[] allFields = BeanUtils.fetchAllFieldWithParentClass(object.getClass());
            for (int index = 0; index < allFields.length; index++) {
                Field field = allFields[index];
                RichUserNameByUserId attr = field.getAnnotation(RichUserNameByUserId.class);
                if (attr == null) {
                    continue;
                }
                String fieldName = field.getName();
                Object userIdObj = ReflectUtils.getFieldValue(object, fieldName);
                if (userIdObj == null) {
                    continue;
                }
                userIdsStr.add(userIdObj + "");
            }
        }

        if (CollectionUtils.isEmpty(userIdsStr)) {
            return;
        }

        Set<Long> userIdsLong = userIdsStr.stream().map(item -> Long.parseLong(item)).collect(Collectors.toSet());

        SysUser querySysUser = new SysUser();
        querySysUser.setQueryLongIds(userIdsLong);
        List<SysUser> sysUsersDB = userMapper.selectUserList(querySysUser);
        if (CollectionUtils.isEmpty(sysUsersDB)) {
            return;
        }
        Map<String, SysUser> userIdKeyMapDB = sysUsersDB.stream().collect(Collectors.toMap(item -> item.getUserId() + "", item -> item));

        for (Object object : list) {
            Field[] allFields = BeanUtils.fetchAllFieldWithParentClass(object.getClass());
            for (int index = 0; index < allFields.length; index++) {
                Field field = allFields[index];
                RichUserNameByUserId attr = field.getAnnotation(RichUserNameByUserId.class);
                if (attr == null) {
                    continue;
                }
                String fieldName = field.getName();
                Object userIdObj = ReflectUtils.getFieldValue(object, fieldName);
                if (userIdObj == null) {
                    continue;
                }

                String targetFieldName = attr.targetFieldName();
                if (StringUtils.isBlank(targetFieldName)) {
                    continue;
                }

                String curUserIdStr = userIdObj + "";
                if (org.apache.commons.lang3.StringUtils.isBlank(curUserIdStr)) {
                    continue;
                }

                SysUser sysUser = userIdKeyMapDB.get(curUserIdStr);
                if (sysUser == null) {
                    continue;
                }
                ReflectUtils.setFieldValue(object, targetFieldName, sysUser.getUserName());
            }
        }
    }

    @Override
    public String fetchUserNameByUserId(Long userId) {
        String userName = null;
        SysUser sysUser = userMapper.selectUserById(userId);
        if (sysUser != null) userName = sysUser.getUserName();
        return userName;
    }

    @Override
    public String fetchLoginNameByUserIdStr(String userIdStr) {
        if (org.apache.commons.lang3.StringUtils.isBlank(userIdStr)) return null;
        SysUser sysUser = selectUserById(Long.parseLong(userIdStr));
        if (sysUser == null) return null;
        return sysUser.getLoginName();
    }

    @Override
    public R batchImport(MultipartFile file, String userId) throws Throwable {
        log.info("上传文件名：" + file.getOriginalFilename());
        R r = new R();
        InputStream fileStream = null;
        InputStream headStream = null;
        try {
            // 获取导入表格数据
            ExcelUtil<SysUser> excelUtil = new ExcelUtil<>();
            fileStream = file.getInputStream();
            List<List<String>> list = excelUtil.importExcel(fileStream);
            // 获取模板数据
            List<String> head = iSysFileService.readExcelTemplate(template);
            // 表格标题头验证
            if (!excelUtil.headCompare(list.get(0), head)) {
                return R.error("导入Excel文件与模板不一致");
            }
            List<SysUser> okList = new ArrayList<SysUser>();
            ArrayList<List<String>> errList = new ArrayList<>();
            // 数据验证
            Map<String, Map<String, String>> validMap = new HashMap<String, Map<String, String>>();
            // 唯一性约束
            Map<String, String> onlyMap = new HashMap<>();
            int okCnt = 0;
            for (int i = 1; i < list.size(); i++) {
                List<String> row = list.get(i);
                String validResult = importRowValid(row, validMap, onlyMap);
                if (validResult == null) {
                    SysUser entity = (SysUser) excelUtil.rowToEntity(row, SysUser.class, head);
                    // @@ 添加附加处理逻辑 例如：关联表更新，状态变化
                    entity.setDelFlag("0"); // 待审核
                    entity.setCreateBy(userId);
                    entity.setUpdateBy(userId);
                    okList.add(entity);
                    okCnt++;
                } else {
                    row.add(validResult);
                    errList.add(row);
                }
            }
            if (okList.size() > 0) {
                userMapper.insertSysUserList(okList);
            }
            r = new R().ok();
            // 整合错误信息存入redis,并设定2分钟过期，无下载自动过期（解决错误文件变为垃圾的问题）
            if (errList.size() > 0) {
                List<String> first = list.get(0);
                first.add("错误信息");
                errList.add(0, first);
                String errKey = UUID.randomUUID().toString();
                redisUtils.set("import-error-" + errKey, errList, 60 * 2);
                // 返回信息
                r.put("errRowCount", errList.size() - 1);
                r.put("errKey", errKey);
            } else {
                r.put("errRowCount", 0);
            }
            r.put("successCount", okCnt);
        } catch (Exception e) {
            log.error("[数据导入异常:{}]", e.getMessage());
            throw e;
            // 不处理，直接抛出异常
        } finally {
            try {
                if (fileStream != null)
                    fileStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (headStream != null)
                    headStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return r;
    }

    private String importRowValid(List<String> row, Map<String, Map<String, String>> validMap, Map<String, String> onlyMap) {
        return "存在异常";
    }

}
