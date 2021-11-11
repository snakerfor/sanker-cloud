package com.snaker.common.utils;

import java.math.BigDecimal;

/**
 * <p>Title: </p>
 * <p>Description: 数据工具类</p>
 * <p>Copyright: Copyright (c) 2019-08-19 14:51</p>
 * <p>Company: </p>
 *
 * @version 1.0
 * @author: snaker
 */
public class NumberUtil {

    public static boolean isNumber(String s) {//合法数字返回true
        //这个正则表达式能够过滤0.0.0、8-99这种不合法的数字
        String reg = "^[-\\\\+]?([0-9]+\\\\.?)?[0-9]+$";
        //String reg="^(\\-|\\+)?\\d+(\\.\\d+)?$";   这个也可以
        return s.matches(reg);
    }


    //判断是否是金额数字
    public static boolean isNumber2(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
