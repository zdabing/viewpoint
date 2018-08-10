package com.viewpoint.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    ERROR(1, "失败"),

    ACTIVITY_NOT_EXIST(13,"活动不存在"),

    ACTIVITY_NOT_STAR(14,"活动未开始"),

    ACTIVITY_IS_END(15,"活动已结束"),

    ARTICLE_NOT_EXIST(23,"文章不存在"),

    USER_NOT_EXIST(33,"用户不存在"),

    EXHIBITS_NOT_EXIST(43,"展品不存在"),

    AREA_NOT_EXIST(53,"景点不存在"),

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
