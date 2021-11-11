/*
 * @(#)BizBusinessServiceImpl.java 2020年1月6日 下午3:38:49
 * Copyright 2020 snaker, Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snaker.activiti.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.snaker.activiti.mapper.BizBusinessMapper;
import com.snaker.common.core.text.Convert;
import com.snaker.common.domain.BizApprove;
import com.snaker.system.feign.RemoteApproveHandleService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.snaker.activiti.consts.ActivitiConstant;
import com.snaker.activiti.domain.BizBusiness;
import com.snaker.activiti.service.IBizBusinessService;
import com.snaker.activiti.service.IBizNodeService;

import tk.mybatis.mapper.entity.Example;

/**
 * <p>File：BizBusinessServiceImpl.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2020 2020年1月6日 下午3:38:49</p>
 * <p>Company: snakerit.com </p>
 *
 * @author snaker
 * @version 1.0
 */
@Service
public class BizBusinessServiceImpl implements IBizBusinessService {
    @Autowired
    private BizBusinessMapper businessMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IBizNodeService bizNodeService;

    @Autowired
    private RemoteApproveHandleService remoteApproveHandleService;

    /**
     * 查询流程业务
     *
     * @param id 流程业务ID
     * @return 流程业务
     */
    @Override
    public BizBusiness selectBizBusinessById(String id) {
        return businessMapper.selectBizBusinessById(id);
    }

    /**
     * 查询流程业务列表
     *
     * @param bizBusiness 流程业务
     * @return 流程业务
     */
    @Override
    public List<BizBusiness> selectBizBusinessList(BizBusiness bizBusiness) {
        return businessMapper.selectBizBusinessList(bizBusiness);
    }

    /**
     * 新增流程业务
     *
     * @param bizBusiness 流程业务
     * @return 结果
     */
    @Override
    public int insertBizBusiness(BizBusiness bizBusiness) {
        return businessMapper.insertBizBusiness(bizBusiness);
    }

    /**
     * 修改流程业务
     *
     * @param bizBusiness 流程业务
     * @return 结果
     */
    @Override
    public int updateBizBusiness(BizBusiness bizBusiness) throws Throwable {
        System.out.println("updateBizBusiness---" + bizBusiness.toString());
        if (bizBusiness.getResult() != null) {
            BizApprove bizApprove = new BizApprove();
            bizApprove.setProcInstId(bizBusiness.getProcInstId());
            bizApprove.setResult(bizBusiness.getResult().toString());
            bizApprove.setStatus(bizBusiness.getStatus().toString());
            System.out.println("updateBizBusiness---" + bizApprove.toString());
            remoteApproveHandleService.handleApprove(bizApprove);
        }
        return businessMapper.updateBizBusiness(bizBusiness);
    }

    /**
     * 删除流程业务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizBusinessByIds(String ids) {
        return businessMapper.deleteBizBusinessById(ids);
    }

    /**
     * 删除流程业务信息
     *
     * @param id 流程业务ID
     * @return 结果
     */
    public int deleteBizBusinessById(String id) {
        return businessMapper.deleteBizBusinessById(id);
    }

    /* (non-Javadoc)
     * @see com.snaker.activiti.service.IBizBusinessService#deleteBizBusinessLogic(java.lang.String)
     */
    @Override
    public int deleteBizBusinessLogic(String ids) {
//        Example example = new Example(BizBusiness.class);
//        example.createCriteria().andIn("id", Lists.newArrayList(ids.split(",")));
//        return businessMapper.updateByExampleSelective(new BizBusiness().setDelFlag(true), example);
        return businessMapper.deleteBizBusinessByIds(Convert.toStrArray(ids));
    }

    /* (non-Javadoc)
     * @see com.snaker.activiti.service.IBizBusinessService#startProcess(com.snaker.activiti.domain.BizBusiness, java.util.Map)
     */
    @Override
    public void startProcess(BizBusiness business, Map<String, Object> variables) throws Throwable{
        // 启动流程用户
        identityService.setAuthenticatedUserId(business.getUserId().toString());
        // 启动流程 需传入业务表id变量
        ProcessInstance pi = runtimeService.startProcessInstanceById(business.getProcDefId(),
                business.getId().toString(), variables);
        // 设置流程实例名称
        runtimeService.setProcessInstanceName(pi.getId(), business.getTitle());
        BizBusiness bizBusiness = new BizBusiness();
        bizBusiness.setId(business.getId());
        bizBusiness.setProcInstId(pi.getId());
        bizBusiness.setProcDefKey(pi.getProcessDefinitionKey());
        // 假如开始就没有任务，那就认为是中止的流程，通常是不存在的
        setAuditor(bizBusiness, ActivitiConstant.RESULT_SUSPEND, business.getUserId());
    }

    @Override
    public BizBusiness startProcessRe(BizBusiness business, Map<String, Object> variables) throws Throwable {
        // 启动流程用户
        identityService.setAuthenticatedUserId(business.getUserId().toString());
        // 启动流程 需传入业务表id变量
        ProcessInstance pi = runtimeService.startProcessInstanceById(business.getProcDefId(),
                business.getId().toString(), variables);
        // 设置流程实例名称
        runtimeService.setProcessInstanceName(pi.getId(), business.getTitle());
        BizBusiness bizBusiness = new BizBusiness();
        bizBusiness.setId(business.getId());
        bizBusiness.setProcInstId(pi.getId());
        bizBusiness.setProcDefKey(pi.getProcessDefinitionKey());
        // 假如开始就没有任务，那就认为是中止的流程，通常是不存在的
        setAuditor(bizBusiness, ActivitiConstant.RESULT_SUSPEND, business.getUserId());
        return bizBusiness;
    }

    @Override
    public int setAuditor(BizBusiness business, int result, long currentUserId) throws Throwable{
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(business.getProcInstId()).list();
        if (null != tasks && tasks.size() > 0) {
            /* 中途驳回处理 by snaker 2020/12/18 8:38 */
            if (result == ActivitiConstant.RESULT_FAIL) {
                business.setCurrentTask(ActivitiConstant.END_TASK_NAME);
                business.setStatus(ActivitiConstant.STATUS_FINISH);
                business.setResult(result);
                taskService.addCandidateUser(tasks.get(0).getId(), "00000000");
            } else {
                Task task = tasks.get(0);
                Set<String> auditors = bizNodeService.getAuditors(task.getTaskDefinitionKey(), currentUserId);
                if (null != auditors && auditors.size() > 0) {
                    // 添加审核候选人
                    for (String auditor : auditors) {
                        taskService.addCandidateUser(task.getId(), auditor);
                    }
                    business.setCurrentTask(task.getName());
//                /* 中途驳回处理 by snaker 2020/12/18 8:38 */
//                if (result == ActivitiConstant.RESULT_FAIL) {
//                    business.setCurrentTask(ActivitiConstant.END_TASK_NAME);
//                    business.setStatus(ActivitiConstant.STATUS_FINISH);
//                    business.setResult(result);
//                }
                } else {
                    runtimeService.deleteProcessInstance(task.getProcessInstanceId(),
                            ActivitiConstant.SUSPEND_PRE + "no auditor");
                    business.setCurrentTask(ActivitiConstant.END_TASK_NAME);
                    business.setStatus(ActivitiConstant.STATUS_SUSPEND);
                    business.setResult(ActivitiConstant.RESULT_SUSPEND);
                }
            }
        } else {
            // 任务结束
            business.setCurrentTask(ActivitiConstant.END_TASK_NAME);
            business.setStatus(ActivitiConstant.STATUS_FINISH);
            business.setResult(result);
        }
        /* 状态回馈审批发起者 by snaker 2020/11/16 14:31 */
        return updateBizBusiness(business);
    }
}
