package com.snaker.system.mapper;

import com.snaker.common.pojo.entity.SysProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 进程mapper
 *
 * @author sfd
 */
@Mapper
public interface SysProcessMapper {

    int insertSysProcess(SysProcess sysProcess);

    int updateSysProcess(SysProcess sysProcess);

    SysProcess getSysProcessById(@Param("processId") Long processId);

    List<SysProcess> selectSysProcessList(SysProcess sysProcess);

}
