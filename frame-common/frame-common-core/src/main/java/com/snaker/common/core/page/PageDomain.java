package com.snaker.common.core.page;

import com.snaker.common.utils.StringUtils;

/**
 * 分页数据
 *
 * @author sfd
 */
public class PageDomain {

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;

    /**
     * 每页显示记录数
     */
    private Integer pageSize;

    /**
     * 排序列
     */
    private String orderByColumn;

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String isAsc;

    public String getOrderBy() {
        if (StringUtils.isEmpty(orderByColumn)) {
            return "";
        }
        /* 添加支持多个排序条件 by snaker 2021/2/23 9:10 */
        String[] orders = orderByColumn.split(",");
        String[] isAscs = null;
        if (StringUtils.isNotEmpty(isAsc)) {
            isAscs = isAsc.split(",");
        } else {
            isAscs = new String[]{};
        }
        StringBuffer orderSqls = new StringBuffer();
        for (int i = 0; i < orders.length; i++) {
            orderSqls.append(StringUtils.toUnderScoreCase(orders[i]));
            if (i < isAscs.length) {
                orderSqls.append(" ").append(isAscs[i]);
            } else {
                orderSqls.append(" ").append("asc");
            }
            orderSqls.append(",");
        }
        return orderSqls.substring(0, orderSqls.length() - 1);
//        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }
}
