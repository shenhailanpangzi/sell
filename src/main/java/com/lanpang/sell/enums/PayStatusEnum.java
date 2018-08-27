package com.lanpang.sell.enums;

import lombok.Getter;

/**
 * Created by 杨浩
 * 2018-06-11 17:16
 */
@Getter
public enum PayStatusEnum implements CodeEnum{

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),

    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
