package com.snaker.activiti.domain;
import com.snaker.common.annotation.Excel;
import com.snaker.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 11对象 act_re_procdef
 *
 * @author snaker
 * @date 2020-11-12
 */
public class ActReProcdef extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long rev;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String category;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String name;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String key;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long version;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String deploymentId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String resourceName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dgrmResourceName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String description;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long hasStartFormKey;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long hasGraphicalNotation;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long suspensionState;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String tenantId;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setRev(Long rev)
    {
        this.rev = rev;
    }

    public Long getRev()
    {
        return rev;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setKey(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setDeploymentId(String deploymentId)
    {
        this.deploymentId = deploymentId;
    }

    public String getDeploymentId()
    {
        return deploymentId;
    }
    public void setResourceName(String resourceName)
    {
        this.resourceName = resourceName;
    }

    public String getResourceName()
    {
        return resourceName;
    }
    public void setDgrmResourceName(String dgrmResourceName)
    {
        this.dgrmResourceName = dgrmResourceName;
    }

    public String getDgrmResourceName()
    {
        return dgrmResourceName;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setHasStartFormKey(Long hasStartFormKey)
    {
        this.hasStartFormKey = hasStartFormKey;
    }

    public Long getHasStartFormKey()
    {
        return hasStartFormKey;
    }
    public void setHasGraphicalNotation(Long hasGraphicalNotation)
    {
        this.hasGraphicalNotation = hasGraphicalNotation;
    }

    public Long getHasGraphicalNotation()
    {
        return hasGraphicalNotation;
    }
    public void setSuspensionState(Long suspensionState)
    {
        this.suspensionState = suspensionState;
    }

    public Long getSuspensionState()
    {
        return suspensionState;
    }
    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantId()
    {
        return tenantId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("rev", getRev())
                .append("category", getCategory())
                .append("name", getName())
                .append("key", getKey())
                .append("version", getVersion())
                .append("deploymentId", getDeploymentId())
                .append("resourceName", getResourceName())
                .append("dgrmResourceName", getDgrmResourceName())
                .append("description", getDescription())
                .append("hasStartFormKey", getHasStartFormKey())
                .append("hasGraphicalNotation", getHasGraphicalNotation())
                .append("suspensionState", getSuspensionState())
                .append("tenantId", getTenantId())
                .toString();
    }
}