package com.snaker.common.pojo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//数据信息
@Data
public class Series implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    @JsonProperty(value = "yAxisIndex")
    private Integer yAxisIndex;

    private List<Object[]> data = new ArrayList<>();

    private String type = "line";

}
