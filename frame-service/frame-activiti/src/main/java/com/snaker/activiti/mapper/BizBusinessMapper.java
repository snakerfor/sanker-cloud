package com.snaker.activiti.mapper;

import com.snaker.activiti.domain.BizBusiness;
import java.util.List;

/**
 * 流程申请Mapper接口
 * 
 * @author snaker
 * @date 2020-11-12
 */
public interface BizBusinessMapper 
{
    /**
     * 查询流程申请
     * 
     * @param id 流程申请ID
     * @return 流程申请
     */
    public BizBusiness selectBizBusinessById(String id);

    /**
     * 查询流程申请列表
     * 
     * @param bizBusiness 流程申请
     * @return 流程申请集合
     */
    public List<BizBusiness> selectBizBusinessList(BizBusiness bizBusiness);

    /**
     * 新增流程申请
     * 
     * @param bizBusiness 流程申请
     * @return 结果
     */
    public int insertBizBusiness(BizBusiness bizBusiness);

    /**
     * 修改流程申请
     * 
     * @param bizBusiness 流程申请
     * @return 结果
     */
    public int updateBizBusiness(BizBusiness bizBusiness);

    /**
     * 删除流程申请
     * 
     * @param id 流程申请ID
     * @return 结果
     */
    public int deleteBizBusinessById(String id);

    /**
     * 批量删除流程申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizBusinessByIds(String[] ids);
}
