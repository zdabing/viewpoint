package com.viewpoint.aspect;

import com.viewpoint.constant.CookieConstant;
import com.viewpoint.exception.ViewpointAuthorizeException;
import com.viewpoint.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 廖师兄
 * 2017-07-30 17:31
 */
@Aspect
@Component
@Slf4j
public class ViewpointAuthorizeAspect {

    /*@Autowired
    private StringRedisTemplate redisTemplate;*/

    @Pointcut("execution(public * com.viewpoint.controller.*Controller.*(..))" +
    "&& !execution(public * com.viewpoint.controller.WeixinController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getServletPath();
        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new ViewpointAuthorizeException(url);
        }

        //去redis里查询
        /*String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new ViewpointAuthorizeException();
        }*/
    }
}
