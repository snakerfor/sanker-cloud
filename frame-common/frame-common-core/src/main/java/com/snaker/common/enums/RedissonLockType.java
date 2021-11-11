package com.snaker.common.enums;

public enum RedissonLockType {

    SYNC_DEPT_USER_LOCK("同步部门人员锁"),

    SYNC_SAP_IPD_DATA_LOCK("同步SAP工艺数据锁"),

    SYNC_SAP_IPD_BOM_DATA_LOCK("实时读取SAP工艺数据 BOM锁"),

    SYNC_SAP_HALF_FINISHED_PRODUCT_LOCK("从SAP同步半成品及外购半成品"),

    SYNC_SAP_MATERIAL_LOCK("从SAP同步原材料"),

    SYNC_SAP_MATERIAL_PRODUCT_LOCK("从SAP同步材料类产成品"),

    SYNC_OA_COST_CENTER_DATA_LOCK("从OA同步成本中心数据"),

    SYNC_SAP_IPD_VERSION_DATA_LOCK("同步SAP生产版本"),

    SYNC_SAP_IPD_WAY_DATA_LOCK("同步SAP工艺路线"),

    SYNC_SAP_IPD_BOMS_DATA_LOCK("同步SAP BOM数据"),

    ;

    private String desc;

    public String getDesc() {
        return desc;
    }

    private RedissonLockType(String desc) {
        this.desc = desc;
    }
}
