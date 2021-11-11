package com.snaker.common.utils;

import org.apache.commons.lang3.StringUtils;
import xyz.downgoon.snowflake.Snowflake;

public class IdUtill {

    private static final Snowflake snowFlake = new Snowflake(1, 2);

    public static String getNextId() {
        return String.valueOf(snowFlake.nextId());
    }

    public static Long getNextLongId(){
        String string = getNextId();
        if(StringUtils.isBlank(string)){
            return null;
        }
        return Long.parseLong(string);
    }

    public static String getNextId(String tabPrefix) {
        return tabPrefix + snowFlake.nextId();
    }
}
