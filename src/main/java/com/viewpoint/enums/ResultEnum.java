package com.viewpoint.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    ACTIVITY_NOT_EXIST(13,"活动不存在"),

    ARTICLE_NOT_EXIST(23,"文章不存在"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
