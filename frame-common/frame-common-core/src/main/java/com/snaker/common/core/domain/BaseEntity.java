package com.snaker.common.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.snaker.common.annotation.RichUserNameByUserId;

import java.io.Serializable;
import java.util.*;

/**
 * Entity基类
 *
 * @author sfd
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //当前登录者 例：SF-1000
    private String currLoginName;

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 创建者
     */
    @RichUserNameByUserId(targetFieldName = "createByUserName")
    private String createBy;
    private String createByUserName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;
    private String updateByUserName;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    private String beginTime;

    private String endTime;

    /**
     * 请求参数
     */
    private Map<String, Object> params;

    //查询使用
    private Set<String> queryIds = new HashSet<>();
    private Set<Long> queryLongIds = new HashSet<>();

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Set<String> getQueryIds() {
        return queryIds;
    }

    public void setQueryIds(Set<String> queryIds) {
        this.queryIds = queryIds;
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

    public Set<Long> getQueryLongIds() {
        return queryLongIds;
    }

    public void setQueryLongIds(Set<Long> queryLongIds) {
        this.queryLongIds = queryLongIds;
    }

    public String getCurrLoginName() {
        return currLoginName;
    }

    public void setCurrLoginName(String currLoginName) {
        this.currLoginName = currLoginName;
    }
}

