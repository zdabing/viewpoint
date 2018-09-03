package com.viewpoint.exception;

import com.viewpoint.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ViewpointException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -168886997047969081L;

    private Integer code = 500;

    private String msg;

    public ViewpointException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ViewpointException(Integer code , String message) {
        super(message);
        this.code = code;
    }
}
