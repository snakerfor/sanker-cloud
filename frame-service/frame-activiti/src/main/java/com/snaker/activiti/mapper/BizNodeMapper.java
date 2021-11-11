package com.snaker.activiti.mapper;

import com.snaker.activiti.domain.BizNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 11Mapper接口
 * 
 * @author snaker
 * @date 2020-11-12
 */
public interface BizNodeMapper 
{
    /**
     * 查询11
     * 
     * @param id 11ID
     * @return 11
     */
    public BizNode selectBizNodeById(String id);

    /**
     * 查询11列表
     * 
     * @param bizNode 11
     * @return 11集合
     */
    public List<BizNode> selectBizNodeList(BizNode bizNode);

    /**
     * 新增11
     * 
     * @param bizNode 11
     * @return 结果
     */
    public int insertBizNode(BizNode bizNode);

    /**
     * 修改11
     * 
     * @param bizNode 11
     * @return 结果
     */
    public int updateBizNode(BizNode bizNode);

    /**
     * 删除11
     * 
     * @param id 11ID
     * @return 结果
     */
    public int deleteBizNodeById(String id);

    /**
     * 批量删除11
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizNodeByIds(String[] ids);

    /**
     * @Description 删除所有节点配置
     * @Param [nodeId]
     * @return void
     * @Author snaker
     * @Date 2020/11/12 10:14
    **/
    public void deleteBizNodeByNodeId(String nodeId);


    public int insertList(@Param("list") List<BizNode> bizNodes);
}
