package com.viewpoint.exception;

import com.viewpoint.enums.ResultEnum;
import lombok.Data;

@Data
public class ViewpointException extends RuntimeException{

    private Integer code;

    public ViewpointException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ViewpointException(Integer code , String message) {
        super(message);
        this.code = code;
    }
}
