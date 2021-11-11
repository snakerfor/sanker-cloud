package com.snaker.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum SysProcessStatusEnum {

    //初始
    INIT,

    //正在处理
    DOING,

    //成功
    SUCCESS,

    //失败
    FAIL;

}
