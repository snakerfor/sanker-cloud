package com.snaker.common.utils;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @Description 数值工具类
 * @Param
 * @return
 * @Author snaker
 * @Date 2020/12/31 16:00
 **/
public class MathUtils {
    /**
     * @return java.lang.Long
     * @Description 求Long[] 数值平均数
     * @Param [longs]
     * @Author snaker
     * @Date 2020/12/31 16:07
     **/
    public static double getAverage(Long[] longs) {
        Long re = Long.valueOf(0);
        int cnt = 0;
        for (Long aLong : longs) {
            if (aLong != null) {
                re += aLong;
                cnt++;
            }
        }
        return cnt == 0 ? 0 : (double)re / cnt;
    }
}
