package com.snaker.common.pojo.entity.lineChart;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//数据信息
@Data
public class CommonLineChartSeries implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type = "line";

    private String name;

    private List<Double> data = new ArrayList<>();


}
