package com.snaker.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CompanyEnum {

    SI_FANG_DA("四方达", "1000", "15a6f9d8dda1f2b99ec3e2e476dbd26d"),

    HUA_YUAN("华源", "2100", "15a6f9f402eb266f450d597443697045"),


    ;

    private String desc;

    private String code;

    private String id;

    private CompanyEnum(String desc, String code, String id) {
        this.desc = desc;
        this.code = code;
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return id;
    }

    public static CompanyEnum fetchCompanyEnumByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            CompanyEnum[] values = CompanyEnum.values();
            for (CompanyEnum value : values) {
                if (value.getCode().equals(code)) {
                    return value;
                }
            }
        }
        return null;
    }
}
