package com.viewpoint.handler;

import com.viewpoint.config.ProjectUrlConfig;
import com.viewpoint.exception.ViewpointAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/4
 * @since 1.0.0
 */
@ControllerAdvice
public class ViewExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(value = ViewpointAuthorizeException.class)
    public ModelAndView handleException(ViewpointAuthorizeException e){
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatMpAuthorize())
                .concat("/weixin/authorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getWechatMpAuthorize())
                .concat(e.getMessage()));
    }
}
