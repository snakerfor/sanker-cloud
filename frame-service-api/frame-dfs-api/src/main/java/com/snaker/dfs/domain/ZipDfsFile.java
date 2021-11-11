package com.snaker.dfs.domain;

import com.alibaba.fastjson.JSON;
import com.snaker.common.core.domain.DirTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZipDfsFile extends DfsFile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dirDataStr = "";

    private Date recordTime;

    private DirTree dirTrees;

    public DirTree getDirTrees() {
        if (StringUtils.isNotBlank(this.dirDataStr)) return JSON.parseObject(this.dirDataStr, DirTree.class);
        return this.dirTrees;
    }
}
