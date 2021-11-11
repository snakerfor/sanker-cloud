package com.snaker.common.domain;

import com.snaker.common.annotation.Excel;
import com.snaker.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 通用审批对象 biz_approve
 * 
 * @author snaker
 * @date 2020-11-13
 */
public class BizApprove extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String procInstId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 数据 */
    @Excel(name = "数据")
    private String record;

    /** 结果状态 */
    @Excel(name = "审批状态")
    private String result;

    /** 状态 */
    @Excel(name = "流程状态")
    private String status;

    /** 流程定义ID */
    @Excel(name = "流程定义ID")
    private String procDefId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setProcInstId(String procInstId) 
    {
        this.procInstId = procInstId;
    }

    public String getProcInstId() 
    {
        return procInstId;
    }
    public void setRecord(String record) 
    {
        this.record = record;
    }

    public String getRecord() 
    {
        return record;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setProcDefId(String procDefId) 
    {
        this.procDefId = procDefId;
    }

    public String getProcDefId() 
    {
        return procDefId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("procInstId", getProcInstId())
            .append("record", getRecord())
            .append("title", getTitle())
            .append("status", getStatus())
            .append("procDefId", getProcDefId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
