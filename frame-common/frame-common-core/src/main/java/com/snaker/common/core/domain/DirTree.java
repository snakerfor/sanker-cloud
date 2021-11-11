package com.snaker.common.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DirTree implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String value;

    private String title;

    private boolean dirFlag;

    private boolean isLeaf;

    private List<DirTree> children = new ArrayList<>();

    public DirTree(String key, String value, boolean dirFlag) {
        this.key = key;
        this.value = value;
        this.title = value;
        this.dirFlag = dirFlag;
    }

    public boolean isLeaf() {
        return !dirFlag;
    }
}
