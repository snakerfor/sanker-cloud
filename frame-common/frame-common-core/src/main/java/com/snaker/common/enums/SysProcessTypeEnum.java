package com.snaker.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SysProcessTypeEnum {

    SYNC_DEPT_USER("SYNC_DEPT_USER", "同步部门及人员", null, RedissonLockType.SYNC_DEPT_USER_LOCK),

    SYNC_SAP_IPD_DATA("SYNC_SAP_IPD_DATA", "同步SAP工艺数据", null, RedissonLockType.SYNC_SAP_IPD_DATA_LOCK),

    SYNC_SAP_IPD_BOM_DATA("SYNC_SAP_IPD_BOM_DATA", "读取SAP BOM", null, RedissonLockType.SYNC_SAP_IPD_BOM_DATA_LOCK),

    SYNC_SAP_HALF_FINISHED_PRODUCT("SYNC_SAP_HALF_FINISHED_PRODUCT", "从SAP同步半成品及外购半成品", 10L * 60, RedissonLockType.SYNC_SAP_HALF_FINISHED_PRODUCT_LOCK),

    SYNC_SAP_MATERIAL("SYNC_SAP_MATERIAL", "从SAP同步原材料", 10L * 60, RedissonLockType.SYNC_SAP_MATERIAL_LOCK),

    SYNC_SAP_MATERIAL_PRODUCT("SYNC_SAP_MATERIAL_PRODUCT", "从SAP同步材料类产成品", 10L * 60, RedissonLockType.SYNC_SAP_MATERIAL_PRODUCT_LOCK),

    SYNC_OA_COST_CENTER_DATA("SYNC_OA_COST_CENTER_DATA", "从OA同步成本中心数据", 3L * 60, RedissonLockType.SYNC_OA_COST_CENTER_DATA_LOCK),

    INSERT_OA_DB_INNER_ORDER_DATA("INSERT_OA_DB_INNER_ORDER_DATA", "往OA数据库添加内部订单数据", null, null),

    SYNC_SAP_IPD_VERSION_DATA("SYNC_SAP_IPD_VERSION_DATA", "同步SAP生产版本", 10L * 60, RedissonLockType.SYNC_SAP_IPD_VERSION_DATA_LOCK),

    SYNC_SAP_IPD_WAY_DATA("SYNC_SAP_IPD_WAY_DATA", "同步SAP工艺路线", 15L * 60, RedissonLockType.SYNC_SAP_IPD_WAY_DATA_LOCK),

    SYNC_SAP_IPD_BOMS_DATA("SYNC_SAP_IPD_BOMS_DATA", "同步SAP BOM数据", 50L * 60, RedissonLockType.SYNC_SAP_IPD_BOMS_DATA_LOCK),

    UPLOAD_IST_NEW_SEM_HISTORY_RESULT("UPLOAD_IST_NEW_SEM_HISTORY_RESULT", "上传历史SEM检测数据", null, null),

    UPLOAD_IST_NEW_VTL_HISTORY_RESULT("UPLOAD_IST_NEW_VTL_HISTORY_RESULT", "上传历史VTL检测数据", null, null),

    UPLOAD_IST_NEW_DT_HISTORY_RESULT("UPLOAD_IST_NEW_DT_HISTORY_RESULT", "上传历史DT检测数据", null, null),

    START_OA_KM_REVIEW("START_OA_KM_REVIEW", "启动OA流程", null, null),

    OA_CALL_RDDS_WEBSERVICE("OA_CALL_RDDS_WEBSERVICE", "OA调用RDDS接口回传流程信息", null, null),

    ;

    private String code;

    private String desc;

    private Long expire;

    private RedissonLockType redissonLockType;

    private SysProcessTypeEnum(String code, String desc, Long expire, RedissonLockType redissonLockType) {
        this.code = code;
        this.desc = desc;
        this.expire = expire;
        this.redissonLockType = redissonLockType;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Long getExpire() {
        return expire;
    }

    public RedissonLockType getRedissonLockType() {
        return redissonLockType;
    }

    public static SysProcessTypeEnum fetchSysProcessTypeEnumByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            SysProcessTypeEnum[] values = SysProcessTypeEnum.values();
            for (SysProcessTypeEnum value : values) {
                if (value.getCode().equalsIgnoreCase(code)) {
                    return value;
                }
            }
        }
        return null;
    }
}
