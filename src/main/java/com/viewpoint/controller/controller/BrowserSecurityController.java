package com.viewpoint.controller.controller;

import com.viewpoint.properties.SecurityConstants;
import com.viewpoint.properties.SecurityProperties;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class BrowserSecurityController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * html结尾的返回登录界面
     * 其他的返回json
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResultVO requireAuthentication(HttpServletRequest request , HttpServletResponse response) throws Exception{
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if (savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是={}",targetUrl);
            /*访问链接 是html 结尾直接跳转登录页面*/
            if (StringUtils.endsWithIgnoreCase(targetUrl,"")){
                redirectStrategy.sendRedirect(request,response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return ResultVOUtil.error(1,"访问服务器需要认证,请登录");
    }
}
