package com.viewpoint.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    UP(1,"已发布"),
    DOWN(0,"未发布"),
    ADMIN(2,"管理员"),
    REGISTERED(1,"已注册"),
    UNREGISTERED(0,"未注册"),
    ACTIVITY_STATUS_BEFORE(0,"未<br>开<br>始"),
    ACTIVITY_STATUS_NOW(1,"报<br>名<br>中"),
    ACTIVITY_STATUS_AFTER(2,"已<br>结<br>束"),
    ;
    private Integer code;

    private String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
