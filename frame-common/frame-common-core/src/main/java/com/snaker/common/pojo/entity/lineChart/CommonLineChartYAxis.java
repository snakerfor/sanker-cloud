package com.snaker.common.pojo.entity.lineChart;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonLineChartYAxis implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type = "value";

    private String nameLocation = "middle";

    private Integer nameGap = 35;

    private String name;

    private Double min = 0D;

    private Double max;
}
