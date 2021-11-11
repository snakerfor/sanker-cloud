/*
 * @(#)BizNodeController.java 2020年1月14日 上午11:36:58
 * Copyright 2020 snaker, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snaker.activiti.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snaker.activiti.consts.ActivitiConstant;
import com.snaker.activiti.service.IBizNodeService;
import com.snaker.activiti.vo.ProcessNodeVo;
import com.snaker.common.core.controller.BaseController;
import com.snaker.common.core.domain.R;

/**
 * <p>File：BizNodeController.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月14日 上午11:36:58</p>
 * <p>Company: snakerit.com </p>
 * @author snaker
 * @version 1.0
 */
@RestController
@RequestMapping("node")
public class BizNodeController extends BaseController
{
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IBizNodeService   bizNodeService;

    /**
     * 获取节点列表
     * 
     * @param proDefId
     * @return
     * @author snaker
     */
    @GetMapping("list/{proDefId}")
    public R list(@PathVariable String proDefId)
    {
        List<ProcessNodeVo> list = new ArrayList<>();
        BpmnModel model = repositoryService.getBpmnModel(proDefId);
        if (model != null)
        {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for (FlowElement element : flowElements)
            {
                ProcessNodeVo node = new ProcessNodeVo();
                node.setNodeId(element.getId());
                node.setName(element.getName());
                if (element instanceof StartEvent)
                {
                    // 开始节点
                    node.setType(ActivitiConstant.NODE_TYPE_START);
                }
                else if (element instanceof UserTask)
                {
                    // 用户任务
                    node.setType(ActivitiConstant.NODE_TYPE_TASK);
                }
                else if (element instanceof EndEvent)
                {
                    // 结束
                    node.setType(ActivitiConstant.NODE_TYPE_END);
                }
                else
                {
                    // 排除其他连线或节点
                    continue;
                }
                list.add(node);
            }
        }
        return result(list);
    }

    /**
     * 获取节点属性
     * 
     * @param nodeId
     * @return
     * @author snaker
     */
    @GetMapping("get/{nodeId}")
    public R get(@PathVariable String nodeId)
    {
        ProcessNodeVo node = new ProcessNodeVo();
        node.setNodeId(nodeId);
        // 设置关联角色，用户，负责人
        node = bizNodeService.setAuditors(node);
        return R.data(node);
    }

    /**
     * 修改节点属性
     * 
     * @param node
     * @return
     * @author snaker
     */
    @PostMapping("update")
    public R update(@RequestBody ProcessNodeVo node)
    {
        bizNodeService.updateBizNode(node);
        return R.ok();
    }
}
