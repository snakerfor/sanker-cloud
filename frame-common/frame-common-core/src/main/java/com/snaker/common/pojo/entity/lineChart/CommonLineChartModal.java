package com.snaker.common.pojo.entity.lineChart;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//普通折线图
@Data
public class CommonLineChartModal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String modalTitle;

    private CommonLineChartXAxis xAxis;

    private CommonLineChartYAxis yAxis;

    private List<CommonLineChartSeries> series = new ArrayList<>();
}
