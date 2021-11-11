package com.snaker.common.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.snaker.common.annotation.Excel;

import java.io.Serializable;
import java.util.*;

/**
 * Entity基类
 *
 * @author sfd
 */
public class RootEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */
    private String createBy;
    @Excel(name = "创建人",type = Excel.Type.EXPORT)
    private String createByUserName;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30,type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;
    @Excel(name = "修改人",type = Excel.Type.EXPORT)
    private String updateByUserName;

    /**
     * 更新时间
     */
    @Excel(name = "修改时间", width = 30,type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    private String beginTime;

    private String endTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 请求参数
     */
    private Map<String, Object> params;

    private Set<String> queryIds = new HashSet<>();

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(String createByUserName) {
        this.createByUserName = createByUserName;
    }

    public String getUpdateByUserName() {
        return updateByUserName;
    }

    public void setUpdateByUserName(String updateByUserName) {
        this.updateByUserName = updateByUserName;
    }

    public Set<String> getQueryIds() {
        return queryIds;
    }

    public void setQueryIds(Set<String> queryIds) {
        this.queryIds = queryIds;
    }
}

