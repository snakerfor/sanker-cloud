package com.snaker.system.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.snaker.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 岗位表 sys_post
 *
 * @author sfd
 */
@Data
public class SysPost extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @JSONField(name = "id")
    private String postId;

    @JSONField(name = "name")
    private String postName;

    //删除标识（0代表存在 2代表删除）
    private String delFlag;

    private String[] persons;

    @JSONField(name = "isAvailable")
    private Boolean oaIsAvailable;

}
