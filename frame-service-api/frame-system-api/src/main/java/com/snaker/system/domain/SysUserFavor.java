package com.snaker.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.snaker.common.annotation.Excel;
import com.snaker.common.core.domain.BaseEntity;

/**
 * 用户喜好对象 sys_user_favor
 * 
 * @author snaker
 * @date 2020-12-02
 */
public class SysUserFavor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 路由路径 */
    @Excel(name = "路由路径")
    private String routerPath;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 记录 */
    @Excel(name = "记录")
    private String record;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRouterPath(String routerPath) 
    {
        this.routerPath = routerPath;
    }

    public String getRouterPath() 
    {
        return routerPath;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setRecord(String record) 
    {
        this.record = record;
    }

    public String getRecord() 
    {
        return record;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("routerPath", getRouterPath())
            .append("type", getType())
            .append("record", getRecord())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
