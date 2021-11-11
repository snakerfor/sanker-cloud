package com.snaker.system.vo;

import lombok.Data;

/**
 * TODO
 *
 * @author snaker
 * @version 1.0
 * @date 2021/1/8 13:33
 */
@Data
public class UserModify {
    private Long userId;
    private String passwordBefore;
    private String passwordAfter;
}
