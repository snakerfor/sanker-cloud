package com.snaker.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum PmProjectExportTypeEnum {

    baseInfo("基本信息"),

    processInfo("项目过程"),

    changeInfo("项目变更"),

    fruitInfo("项目成果"),

    closingInfo("项目结项"),

    sumIncomeInfo("项目收益汇总"),

    detailIncomeInfo("项目收益明细"),

    mainRiskInfo("项目风险"),

    sumExpenseInfo("项目费用汇总"),

    detailExpenseInfo("项目费用明细"),

    ipConditionInfo("知识产权检索条件"),

    ipResultInfo("知识产权检索结果"),

    ipPlanInfo("知识产权规划"),

    ipTractInfo("知识产权跟踪情况"),

    ;

    private String desc;

    private PmProjectExportTypeEnum(String desc) {
        this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }

    public static PmProjectExportTypeEnum fetchPmProjectExportTypeEnumByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            PmProjectExportTypeEnum[] values = PmProjectExportTypeEnum.values();
            for (PmProjectExportTypeEnum value : values) {
                if (value.name().equals(name)) {
                    return value;
                }
            }
        }
        return null;
    }

    public static List<PmProjectExportTypeEnum> fetchPmProjectExportTypeEnumByNames(String[] names) {
        List<PmProjectExportTypeEnum> result = new ArrayList<>();
        if (names.length > 0) {
            for (String name : names) {
                PmProjectExportTypeEnum pmProjectExportTypeEnum = fetchPmProjectExportTypeEnumByName(name);
                if (pmProjectExportTypeEnum != null) {
                    result.add(pmProjectExportTypeEnum);
                }
            }
        }
        return result;
    }
}


