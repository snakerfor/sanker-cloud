package com.snaker.system.service;

import com.snaker.common.enums.SysProcessStatusEnum;
import com.snaker.common.enums.SysProcessTypeEnum;
import com.snaker.common.pojo.entity.SysProcess;
import com.snaker.common.pojo.entity.TwoTuple;
import com.snaker.common.enums.RedissonLockType;

import java.util.List;

public interface ISysProcessService {

    SysProcess addSysProcess(SysProcessTypeEnum sysProcessTypeEnum, SysProcessStatusEnum sysProcessStatusEnum, String sysProcessData);

    void updateSysProcess(SysProcess sysProcess);

    SysProcess getSysProcessById(Long processId);

    List<SysProcess> selectSysProcessList(SysProcess sysProcess);

    TwoTuple<SysProcess, Boolean> initProcess(Boolean scheduleFlag,  SysProcessTypeEnum processTypeEnum) throws Exception;
}
