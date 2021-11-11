package com.snaker.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum FileTypeEnum {

    IMAGE("图片", new String[]{"jpg", "jpeg", "png", "bmp"}),

    WORD("文档", new String[]{"doc", "docx"}),

    EXCEL("表格", new String[]{"xls", "xlsx"}),

    PPT("ppt", new String[]{"ppt", "pptx"}),

    PDF("pdf", new String[]{"pdf"}),

    DWG("CAD", new String[]{"dwg"}),

    ZIP("ZIP", new String[]{"zip"}),

    ;


    private String desc;

    private String[] fileSuffix;

    private FileTypeEnum(String desc, String[] fileSuffix) {
        this.desc = desc;
        this.fileSuffix = fileSuffix;
    }


    public String getDesc() {
        return desc;
    }

    public String[] getFileSuffix() {
        return fileSuffix;
    }

    public static FileTypeEnum getFileTypeEnumByFileSuffix(String fileSuffix) {
        if (StringUtils.isNotBlank(fileSuffix)) {
            fileSuffix = fileSuffix.toLowerCase();
            FileTypeEnum[] values = FileTypeEnum.values();
            for (FileTypeEnum value : values) {
                if (Arrays.asList(value.getFileSuffix()).contains(fileSuffix)) {
                    return value;
                }
            }
        }
        return null;
    }

}
