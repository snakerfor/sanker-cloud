package com.snaker.activiti.domain;

import com.snaker.common.annotation.Excel;
import com.snaker.common.core.domain.BaseEntity;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 11对象 biz_node
 * 
 * @author snaker
 * @date 2020-11-12
 */
public class BizNode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 节点ID */
    @Excel(name = "节点ID")
    private String nodeId;

    /** 类型 1：角色 2：部门负责人 3：用户 4：所属部门负责人 */
    @Excel(name = "类型 1：角色 2：部门负责人 3：用户 4：所属部门负责人")
    private Integer type;

    /** 类型对应负责人的值 */
    @Excel(name = "类型对应负责人的值")
    private Long auditor;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAuditor() {
        return auditor;
    }

    public void setAuditor(Long auditor) {
        this.auditor = auditor;
    }

    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getNodeId() 
    {
        return nodeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nodeId", getNodeId())
            .append("nodeId", getNodeId())
            .append("type", getType())
            .append("auditor", getAuditor())
            .toString();
    }
}
