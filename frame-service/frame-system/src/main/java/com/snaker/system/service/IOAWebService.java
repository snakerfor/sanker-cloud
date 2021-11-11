package com.snaker.system.service;

import com.snaker.common.pojo.entity.SysProcess;
import com.snaker.common.pojo.entity.TwoTuple;

public interface IOAWebService {

    TwoTuple<SysProcess, Boolean> initSyncDeptAndUserProcess(Boolean scheduleFlag) throws Exception;

    void asyncSyncDeptAndUser(SysProcess sysProcess);

}
