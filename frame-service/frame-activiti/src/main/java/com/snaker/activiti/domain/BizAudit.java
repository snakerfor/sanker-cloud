package com.snaker.activiti.domain;

import com.snaker.common.annotation.Excel;
import com.snaker.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Transient;
import java.util.Arrays;
import java.util.Date;

/**
 * 业务审核记录对象 biz_audit
 * 
 * @author snaker
 * @date 2020-11-12
 */
public class BizAudit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 任务编号 */
    @Excel(name = "任务编号")
    private String taskId;

    /** 审核结果 */
    @Excel(name = "审核结果")
    private Integer result;

    /** 审核意见 */
    @Excel(name = "审核意见")
    private String comment;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String procName;

    /** 流程定义key */
    @Excel(name = "流程定义key")
    private String procDefKey;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applyer;

    /** 审批人 */
    @Excel(name = "审批人")
    private String auditor;

    /** 审批人编号 */
    @Excel(name = "审批人编号")
    private Long auditorId;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Date createTime;

    /** 申请时间 */
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 删除标记 */
    private String delFlag;

    private String   procInstId;

    private String     businessKey;

    private String[] taskIds;


    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setTaskId(String taskId) 
    {
        this.taskId = taskId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }
    public void setProcName(String procName) 
    {
        this.procName = procName;
    }

    public String getProcName() 
    {
        return procName;
    }
    public void setProcDefKey(String procDefKey) 
    {
        this.procDefKey = procDefKey;
    }

    public String getProcDefKey() 
    {
        return procDefKey;
    }
    public void setApplyer(String applyer) 
    {
        this.applyer = applyer;
    }

    public String getApplyer() 
    {
        return applyer;
    }
    public void setAuditor(String auditor) 
    {
        this.auditor = auditor;
    }

    public String getAuditor() 
    {
        return auditor;
    }
    public void setAuditorId(Long auditorId) 
    {
        this.auditorId = auditorId;
    }

    public Long getAuditorId() 
    {
        return auditorId;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String[] getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(String[] taskIds) {
        this.taskIds = taskIds;
    }

    @Override
    public String toString() {
        return "BizAudit{" +
                "id='" + id + '\'' +
                ", taskId='" + taskId + '\'' +
                ", result=" + result +
                ", comment='" + comment + '\'' +
                ", procName='" + procName + '\'' +
                ", procDefKey='" + procDefKey + '\'' +
                ", applyer='" + applyer + '\'' +
                ", auditor='" + auditor + '\'' +
                ", auditorId=" + auditorId +
                ", createTime=" + createTime +
                ", applyTime=" + applyTime +
                ", delFlag='" + delFlag + '\'' +
                ", procInstId='" + procInstId + '\'' +
                ", businessKey=" + businessKey +
                ", taskIds=" + Arrays.toString(taskIds) +
                '}';
    }
}
