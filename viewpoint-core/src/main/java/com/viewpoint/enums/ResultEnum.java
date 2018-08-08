package com.viewpoint.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    ERROR(1, "失败"),

    ACTIVITY_NOT_EXIST(13,"活动不存在"),

    ACTIVITY_ORDER_IS_EXIST(11,"请不要重复申请"),

    ARTICLE_NOT_EXIST(23,"文章不存在"),

    USER_NOT_EXIST(33,"用户不存在"),

    EXHIBITS_NOT_EXIST(43,"展品不存在"),

    TIME_IS_AFTEr(51,"活动已结束"),

    TIME_IS_BEFORE(52,"活动还未开始"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
