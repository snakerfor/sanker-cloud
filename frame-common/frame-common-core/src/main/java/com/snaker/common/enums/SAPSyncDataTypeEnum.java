package com.snaker.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SAPSyncDataTypeEnum {

    HALF_FINISHED_PRODUCT("半成品及外购半成品", SysProcessTypeEnum.SYNC_SAP_HALF_FINISHED_PRODUCT, "ZPPF017"),

    MATERIAL("原材料", SysProcessTypeEnum.SYNC_SAP_MATERIAL, "ZPPF017"),

    MATERIAL_PRODUCT("材料类产成品", SysProcessTypeEnum.SYNC_SAP_MATERIAL_PRODUCT, "ZPPF017"),

    IPD_VERSION("生产版本", SysProcessTypeEnum.SYNC_SAP_IPD_VERSION_DATA, "ZPPF011"),

    IPD_WAY("工艺路线", SysProcessTypeEnum.SYNC_SAP_IPD_WAY_DATA, "ZPPF013"),

    IPD_BOMS("BOMS", SysProcessTypeEnum.SYNC_SAP_IPD_BOMS_DATA, "ZPPF016"),

    ;


    private String desc;

    private SysProcessTypeEnum sysProcessTypeEnum;

    private String functionName;

    private SAPSyncDataTypeEnum(String desc, SysProcessTypeEnum sysProcessTypeEnum, String functionName) {
        this.desc = desc;
        this.sysProcessTypeEnum = sysProcessTypeEnum;
        this.functionName = functionName;
    }

    public String getDesc() {
        return desc;
    }

    public SysProcessTypeEnum getSysProcessTypeEnum() {
        return sysProcessTypeEnum;
    }

    public String getFunctionName() {
        return functionName;
    }
}
