package com.snaker.common.pojo.entity.lineChart;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommonLineChartXAxis implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type = "category";

    private String nameLocation = "middle";

    private Integer nameGap = 35;

    private String name;

    private List<String> data = new ArrayList<>();
}
