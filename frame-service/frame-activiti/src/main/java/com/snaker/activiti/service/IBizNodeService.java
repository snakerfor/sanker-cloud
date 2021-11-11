package com.snaker.activiti.service;

import com.snaker.activiti.domain.BizNode;
import com.snaker.activiti.vo.ProcessNodeVo;

import java.util.List;
import java.util.Set;

/**
 * 节点Service接口
 * 
 * @author sfd
 * @date 2020-01-07
 */
public interface IBizNodeService
{
    /**
     * 查询节点
     * 
     * @param id 节点ID
     * @return 节点
     */
    public BizNode selectBizNodeById(String id);

    /**
     * 查询节点列表
     * 
     * @param bizNode 节点
     * @return 节点集合
     */
    public List<BizNode> selectBizNodeList(BizNode bizNode);

    /**
     * 新增节点
     * 
     * @param bizNode 节点
     * @return 结果
     */
    public int insertBizNode(BizNode bizNode);

    /**
     * 设置节点视图
     * @param node
     * @author snaker
     * @return 
     */
    public ProcessNodeVo setAuditors(ProcessNodeVo node);

    /**
     * 更新节点配置
     * 
     * @param node
     * @return
     * @author snaker
     */
    public int updateBizNode(ProcessNodeVo node);

    /**
     * 根据节点id获取所有审核人的编号
     * @param nodeId 流程节点编号
     * @param userId 当前用户编号
     * @return
     * @author snaker
     */
    public Set<String> getAuditors(String nodeId,long userId);
}
