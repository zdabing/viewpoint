package com.viewpoint.vo;

import lombok.Data;

/**
 * HTTP返回的最外层对象
 */
@Data
public class ResultVO<T> {

    /** 错误码 */
    private Integer code;

    /** 信息 */
    private String msg;

    /** 数量 */
    private Long count;

    private T data;
}
