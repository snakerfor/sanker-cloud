package com.snaker.system.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.snaker.common.core.domain.BaseEntity;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * 部门表 sys_dept
 *
 * @author sfd
 */
@ToString
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    @JSONField(name = "name")
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 负责人编号
     */
    private Long leaderId;

    @JSONField(name = "thisLeader")
    private String leaderPost;

    @JSONField(name = "superLeader")
    private String superLeaderPost;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态:0正常,1停用
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 父部门名称
     */
    private String parentName;

    //oa的id
    @JSONField(name = "id")
    private String oaId;
    private Set<String> oaIds = new HashSet<>();

    @JSONField(name = "parent")
    private String oaParentId;

    @JSONField(name = "isAvailable")
    private Boolean oaIsAvailable;

    private Set<String> deptNames = new HashSet<>();

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getOaId() {
        return oaId;
    }

    public void setOaId(String oaId) {
        this.oaId = oaId;
    }

    public Boolean getOaIsAvailable() {
        return oaIsAvailable;
    }

    public void setOaIsAvailable(Boolean oaIsAvailable) {
        this.oaIsAvailable = oaIsAvailable;
    }

    public String getOaParentId() {
        return oaParentId;
    }

    public void setOaParentId(String oaParentId) {
        this.oaParentId = oaParentId;
    }

    public String getLeaderPost() {
        return leaderPost;
    }

    public void setLeaderPost(String leaderPost) {
        this.leaderPost = leaderPost;
    }

    public String getSuperLeaderPost() {
        return superLeaderPost;
    }

    public void setSuperLeaderPost(String superLeaderPost) {
        this.superLeaderPost = superLeaderPost;
    }

    public Set<String> getDeptNames() {
        return deptNames;
    }

    public void setDeptNames(Set<String> deptNames) {
        this.deptNames = deptNames;
    }

    public Set<String> getOaIds() {
        return oaIds;
    }

    public void setOaIds(Set<String> oaIds) {
        this.oaIds = oaIds;
    }
}
