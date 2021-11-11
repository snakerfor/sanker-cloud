package com.snaker.common.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExportSheet<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sheetName;

    public Class<T> clazz;

    private List<T> list;

    private String[] outFields = {};

    private String[] noExportFields = {};

    private boolean sortByOutFieldsFlag = false;

    public ExportSheet(String sheetName, Class<T> clazz, List<T> list, String[] noExportFields) {
        this.sheetName = sheetName;
        this.clazz = clazz;
        this.list = list;
        this.noExportFields = noExportFields;
    }




    public ExportSheet(String sheetName, Class<T> clazz, List<T> list, String[] outFields,boolean sortByOutFieldsFlag) {
        this.sheetName = sheetName;
        this.clazz = clazz;
        this.list = list;
        this.outFields = outFields;
        this.sortByOutFieldsFlag = sortByOutFieldsFlag;
        if(!sortByOutFieldsFlag)throw new RuntimeException("该参数不得为false");
    }
}
