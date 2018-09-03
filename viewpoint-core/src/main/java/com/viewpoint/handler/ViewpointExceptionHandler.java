package com.viewpoint.handler;

import com.viewpoint.exception.ViewpointException;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理器
 */

@Slf4j
@ControllerAdvice
public class ViewpointExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    /*@InitBinder
    public void initBinder(WebDataBinder binder) {
        log.info("请求有参数才进来: {}",binder);
    }*/

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    /*@ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "ZTB");
    }*/

    @ExceptionHandler(value = ViewpointException.class)
    @ResponseBody
    public ResultVO handleException(ViewpointException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    /*@ExceptionHandler(Exception.class)
    public Object handleException(Exception e,HttpServletRequest req){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Integer statusCode = (Integer)req.getAttribute("javax.servlet.error.status_code");
        if (!StringUtils.isEmpty(statusCode)) {
            status.valueOf(statusCode);
        }
        AjaxObject r = new AjaxObject();
        //业务异常
        if(e instanceof ViewpointException){
            r.put("code", ((ViewpointException) e).getCode());
            r.put("msg", ((ViewpointException) e).getMsg());
        }else{//系统异常
            r.put("code",status.value());
            r.put("msg","未知异常，请联系管理员");
        }
        //使用HttpServletRequest中的header检测请求是否为ajax, 如果是ajax则返回json, 如果为非ajax则返回view(即ModelAndView)
        String contentTypeHeader = req.getHeader("Content-Type");
        String acceptHeader = req.getHeader("Accept");
        String xRequestedWith = req.getHeader("X-Requested-With");
        if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
            return r;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", req.getRequestURL());
            modelAndView.addObject("status", status.value());
            modelAndView.addObject("stackTrace", e.getStackTrace());
            modelAndView.setViewName("error/error");
            return modelAndView;
        }
    }*/
}
