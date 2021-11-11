package com.snaker.common.pojo.entity;

import lombok.Data;

import java.io.Serializable;

//Y轴基础信息
@Data
public class YAxis implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer max;

    private Integer min = 0;

    private String type = "value";

    private String nameLocation = "middle";

    private Integer nameGap = 35;
}
