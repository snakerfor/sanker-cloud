package com.snaker.activiti.mapper;

import java.util.List;

import com.snaker.activiti.vo.HiProcInsVo;

public interface HistoryMapper
{
    List<HiProcInsVo> getHiProcInsListDone(HiProcInsVo hiProcInsVo);
}