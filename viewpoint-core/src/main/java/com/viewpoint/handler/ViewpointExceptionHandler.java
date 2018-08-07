package com.viewpoint.handler;

import com.viewpoint.exception.ViewpointException;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ViewpointExceptionHandler {

    @ExceptionHandler(value = ViewpointException.class)
    @ResponseBody
    public ResultVO handerViewpointException(ViewpointException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }
}
