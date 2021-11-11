package com.snaker.activiti.domain;

import com.snaker.common.annotation.Excel;
import com.snaker.common.core.domain.BaseEntity;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 流程申请对象 biz_business
 * 
 * @author snaker
 * @date 2020-11-12
 */
public class BizBusiness extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 流程定义编号 */
    @Excel(name = "流程定义编号")
    private String procDefId;

    /** 流程定义key */
    @Excel(name = "流程定义key")
    private String procDefKey;

    /** 流程实例编号 */
    @Excel(name = "流程实例编号")
    private String procInstId;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String procName;

    /** 当前任务节点 */
    @Excel(name = "当前任务节点")
    private String currentTask;

    /** 结果状态 */
    @Excel(name = "结果状态")
    private Integer result;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 关联表ID */
    @Excel(name = "关联表ID")
    private String tableId;

    /** 申请标题 */
    @Excel(name = "申请标题")
    private String title;

    /** 创建用户ID */
    @Excel(name = "创建用户ID")
    private Long userId;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applyer;

    /** 申请时间 */
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 删除标记 */
    private String delFlag;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setProcDefId(String procDefId) 
    {
        this.procDefId = procDefId;
    }

    public String getProcDefId() 
    {
        return procDefId;
    }
    public void setProcDefKey(String procDefKey) 
    {
        this.procDefKey = procDefKey;
    }

    public String getProcDefKey() 
    {
        return procDefKey;
    }
    public void setProcInstId(String procInstId) 
    {
        this.procInstId = procInstId;
    }

    public String getProcInstId() 
    {
        return procInstId;
    }
    public void setProcName(String procName) 
    {
        this.procName = procName;
    }

    public String getProcName() 
    {
        return procName;
    }
    public void setCurrentTask(String currentTask) 
    {
        this.currentTask = currentTask;
    }

    public String getCurrentTask() 
    {
        return currentTask;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTableId(String tableId)
    {
        this.tableId = tableId;
    }

    public String getTableId() 
    {
        return tableId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setApplyer(String applyer) 
    {
        this.applyer = applyer;
    }

    public String getApplyer() 
    {
        return applyer;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("procDefId", getProcDefId())
            .append("procDefKey", getProcDefKey())
            .append("procInstId", getProcInstId())
            .append("procName", getProcName())
            .append("currentTask", getCurrentTask())
            .append("result", getResult())
            .append("status", getStatus())
            .append("tableId", getTableId())
            .append("title", getTitle())
            .append("userId", getUserId())
            .append("applyer", getApplyer())
            .append("applyTime", getApplyTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
