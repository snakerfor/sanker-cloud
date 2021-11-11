/*
 * @(#)BizAuditServiceImpl.java 2020年1月6日 下午3:38:49
 * Copyright 2020 snaker, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snaker.activiti.service.impl;

import java.util.List;

import com.snaker.activiti.domain.BizAudit;
import com.snaker.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snaker.activiti.mapper.BizAuditMapper;
import com.snaker.activiti.service.IBizAuditService;
import com.snaker.activiti.vo.HiTaskVo;

/**
 * <p>File：BizAuditServiceImpl.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月6日 下午3:38:49</p>
 * <p>Company: snakerit.com </p>
 * @author snaker
 * @version 1.0
 */
@Service
public class BizAuditServiceImpl implements IBizAuditService
{
    @Autowired
    private BizAuditMapper auditMapper;

    /**
     * 查询审核记录
     * 
     * @param id 审核记录ID
     * @return 审核记录
     */
    @Override
    public BizAudit selectBizAuditById(String id)
    {
        return auditMapper.selectBizAuditById(id);
    }


    /**
     * 查询审核记录列表
     * 
     * @param bizAudit 审核记录
     * @return 审核记录
     */
    @Override
    public List<BizAudit> selectBizAuditList(BizAudit bizAudit)
    {
        return auditMapper.selectBizAuditList(bizAudit);
    }

    /**
     * 新增审核记录
     * 
     * @param bizAudit 审核记录
     * @return 结果
     */
    @Override
    public int insertBizAudit(BizAudit bizAudit)
    {
        return auditMapper.insertBizAudit(bizAudit);
    }

    /**
     * 修改审核记录
     * 
     * @param bizAudit 审核记录
     * @return 结果
     */
    @Override
    public int updateBizAudit(BizAudit bizAudit)
    {
        return auditMapper.updateBizAudit(bizAudit);
    }

    /**
     * 删除审核记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizAuditByIds(String ids)
    {
        return auditMapper.deleteBizAuditByIds(Convert.toStrArray(ids));
    }
    /**
     * 删除审核记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizAuditLogic(String ids)
    {
        String[] idArr=ids.split(",");
        return auditMapper.deleteLogic(idArr);
    }


    /**
     * 删除审核记录信息
     * 
     * @param id 审核记录ID
     * @return 结果
     */
    public int deleteBizAuditById(String id)
    {
        return auditMapper.deleteBizAuditById(id);
    }

    /* (non-Javadoc)
     * @see com.snaker.activiti.service.IBizAuditService#getHistoryTaskList(com.snaker.activiti.vo.HiTaskVo)
     */
    @Override
    public List<HiTaskVo> getHistoryTaskList(HiTaskVo hiTaskVo)
    {
        return auditMapper.getHistoryTaskList(hiTaskVo);
    }
}
