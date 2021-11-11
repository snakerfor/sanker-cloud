package com.snaker.common.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//双Y轴结果模型
@Data
public class DoubleYAxisLineChartModal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String modalTitle;

    private String xAxisName;

    private List<Integer> xAxisData = new ArrayList<>();

    private List<YAxis> yAxis = new ArrayList<>();

    private List<Series> series = new ArrayList<>();
}
