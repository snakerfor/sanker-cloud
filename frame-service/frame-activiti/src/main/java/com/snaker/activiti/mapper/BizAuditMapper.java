package com.snaker.activiti.mapper;

import com.snaker.activiti.domain.BizAudit;
import com.snaker.activiti.vo.HiTaskVo;
import java.util.List;

/**
 * 业务审核记录Mapper接口
 * 
 * @author snaker
 * @date 2020-11-12
 */
public interface BizAuditMapper 
{
    /**
     * 查询业务审核记录
     * 
     * @param id 业务审核记录ID
     * @return 业务审核记录
     */
    public BizAudit selectBizAuditById(String id);

    /**
     * 查询业务审核记录列表
     * 
     * @param bizAudit 业务审核记录
     * @return 业务审核记录集合
     */
    public List<BizAudit> selectBizAuditList(BizAudit bizAudit);

    /**
     * 新增业务审核记录
     * 
     * @param bizAudit 业务审核记录
     * @return 结果
     */
    public int insertBizAudit(BizAudit bizAudit);

    /**
     * 修改业务审核记录
     * 
     * @param bizAudit 业务审核记录
     * @return 结果
     */
    public int updateBizAudit(BizAudit bizAudit);

    /**
     * 删除业务审核记录
     * 
     * @param id 业务审核记录ID
     * @return 结果
     */
    public int deleteBizAuditById(String id);

    /**
     * 批量删除业务审核记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizAuditByIds(String[] ids);


    List<HiTaskVo> getHistoryTaskList(HiTaskVo hiTaskVo);

    /**
     * logic删除
     * @param ids
     * @return
     * @author snaker
     */
    int deleteLogic(String[] ids);
}
