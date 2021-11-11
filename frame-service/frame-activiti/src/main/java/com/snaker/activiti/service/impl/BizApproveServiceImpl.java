package com.snaker.activiti.service.impl;

import java.util.List;

import com.snaker.activiti.mapper.BizApproveMapper;
import com.snaker.activiti.service.IBizApproveService;
import com.snaker.common.domain.BizApprove;
import com.snaker.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.snaker.common.core.text.Convert;

/**
 * 通用审批Service业务层处理
 * 
 * @author snaker
 * @date 2020-11-13
 */
@Service
public class BizApproveServiceImpl implements IBizApproveService
{
    @Autowired
    private BizApproveMapper bizApproveMapper;

    /**
     * 查询通用审批
     * 
     * @param id 通用审批ID
     * @return 通用审批
     */
    @Override
    public BizApprove selectBizApproveById(String id)
    {
        return bizApproveMapper.selectBizApproveById(id);
    }

    /**
     * 查询通用审批列表
     * 
     * @param bizApprove 通用审批
     * @return 通用审批
     */
    @Override
    public List<BizApprove> selectBizApproveList(BizApprove bizApprove)
    {
        return bizApproveMapper.selectBizApproveList(bizApprove);
    }

    /**
     * 新增通用审批
     * 
     * @param bizApprove 通用审批
     * @return 结果
     */
    @Override
    public int insertBizApprove(BizApprove bizApprove)
    {
        bizApprove.setCreateTime(DateUtils.getNowDate());
        return bizApproveMapper.insertBizApprove(bizApprove);
    }

    /**
     * 修改通用审批
     * 
     * @param bizApprove 通用审批
     * @return 结果
     */
    @Override
    public int updateBizApprove(BizApprove bizApprove)
    {
        bizApprove.setUpdateTime(DateUtils.getNowDate());
        return bizApproveMapper.updateBizApprove(bizApprove);
    }

    /**
     * 删除通用审批对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizApproveByIds(String ids)
    {
        return bizApproveMapper.deleteBizApproveByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除通用审批信息
     * 
     * @param id 通用审批ID
     * @return 结果
     */
    public int deleteBizApproveById(String id)
    {
        return bizApproveMapper.deleteBizApproveById(id);
    }
}
