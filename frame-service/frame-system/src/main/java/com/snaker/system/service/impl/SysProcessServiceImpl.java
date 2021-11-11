package com.snaker.system.service.impl;

import com.snaker.common.enums.RedissonLockType;
import com.snaker.common.enums.SysProcessStatusEnum;
import com.snaker.common.enums.SysProcessTypeEnum;
import com.snaker.common.pojo.entity.SysProcess;
import com.snaker.common.pojo.entity.TwoTuple;
import com.snaker.common.redis.util.RedisUtils;
import com.snaker.system.mapper.SysProcessMapper;
import com.snaker.system.service.ISysProcessService;
import com.snaker.system.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class SysProcessServiceImpl implements ISysProcessService {

    @Autowired
    private SysProcessMapper sysProcessMapper;

    @Autowired
    @Lazy
    RedissonUtils redissonUtils;

    @Autowired
    @Lazy
    private RedisUtils redisUtils;


    @Override
    public SysProcess addSysProcess(SysProcessTypeEnum sysProcessTypeEnum, SysProcessStatusEnum sysProcessStatusEnum, String sysProcessData) {
        SysProcess sysProcess = new SysProcess();
        sysProcess.setProcessType(sysProcessTypeEnum);
        sysProcess.setProcessStatus(sysProcessStatusEnum);
        sysProcess.setProcessData(sysProcessData);
        sysProcessMapper.insertSysProcess(sysProcess);
        return sysProcess;
    }


    @Override
    public void updateSysProcess(SysProcess sysProcess) {
        sysProcessMapper.updateSysProcess(sysProcess);
    }

    @Override
    public SysProcess getSysProcessById(Long processId) {
        return sysProcessMapper.getSysProcessById(processId);
    }

    @Override
    public List<SysProcess> selectSysProcessList(SysProcess sysProcess) {
        return sysProcessMapper.selectSysProcessList(sysProcess);
    }

    @Override
    public TwoTuple<SysProcess, Boolean> initProcess(Boolean scheduleFlag, SysProcessTypeEnum processTypeEnum) throws Exception {
        SysProcess sysProcess = null;
        String processTypeEnumName = processTypeEnum.name();
        String processTypeEnumDesc = processTypeEnum.getDesc();
        String sysProcessData = scheduleFlag ? "定时" : "手动";

        RedissonLockType redissonLockType = processTypeEnum.getRedissonLockType();
        if (redissonLockType == null) {
            //无需锁，直接执行
            return new TwoTuple(addSysProcess(processTypeEnum, SysProcessStatusEnum.DOING, sysProcessData), true);
        }
        
        long expire = processTypeEnum.getExpire() == null ? 5 * 60 : processTypeEnum.getExpire();

        RLock lock = redissonUtils.getLock(redissonLockType);
        try {
            boolean lockResult = lock.tryLock(1, TimeUnit.MINUTES);
            log.info("[{}]获取分布式锁结果:{}", redissonLockType.getDesc(), lockResult);

            String processIdStr = redisUtils.get(processTypeEnumName);
            if (StringUtils.isNotBlank(processIdStr)) {
                sysProcess = getSysProcessById(Long.parseLong(processIdStr));
                log.info("[已存在一个[{}]的进程:{}]", processTypeEnumDesc, sysProcess);
                return new TwoTuple(sysProcess, false);
            }

            sysProcess = addSysProcess(processTypeEnum, SysProcessStatusEnum.DOING, sysProcessData);
            log.info("[新建一个[{}]进程:{}]", processTypeEnumDesc, sysProcess);

            redisUtils.set(processTypeEnumName, sysProcess.getId() + "", expire);
            return new TwoTuple(sysProcess, true);
        } finally {
            if (lock != null) {
                lock.unlock();
                log.info("[{}]释放了分布式锁", redissonLockType.getDesc());
            }
        }
    }

}
