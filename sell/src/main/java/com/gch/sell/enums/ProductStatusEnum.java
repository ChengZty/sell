package com.gch.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 */
@Getter
public enum ProductStatusEnum {

    UP(0,"在架"),
    DOWN(1,"下架")
    ;

    /** 状态码. */
    private Integer code ;

    /** 状态信息. */
    private String message ;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
