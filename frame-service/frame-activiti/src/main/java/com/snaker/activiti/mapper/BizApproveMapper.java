package com.snaker.activiti.mapper;

import com.snaker.common.domain.BizApprove;

import java.util.List;

/**
 * 通用审批Mapper接口
 * 
 * @author snaker
 * @date 2020-11-13
 */
public interface BizApproveMapper 
{
    /**
     * 查询通用审批
     * 
     * @param id 通用审批ID
     * @return 通用审批
     */
    public BizApprove selectBizApproveById(String id);

    /**
     * 查询通用审批列表
     * 
     * @param bizApprove 通用审批
     * @return 通用审批集合
     */
    public List<BizApprove> selectBizApproveList(BizApprove bizApprove);

    /**
     * 新增通用审批
     * 
     * @param bizApprove 通用审批
     * @return 结果
     */
    public int insertBizApprove(BizApprove bizApprove);

    /**
     * 修改通用审批
     * 
     * @param bizApprove 通用审批
     * @return 结果
     */
    public int updateBizApprove(BizApprove bizApprove);

    /**
     * 删除通用审批
     * 
     * @param id 通用审批ID
     * @return 结果
     */
    public int deleteBizApproveById(String id);

    /**
     * 批量删除通用审批
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizApproveByIds(String[] ids);
}
