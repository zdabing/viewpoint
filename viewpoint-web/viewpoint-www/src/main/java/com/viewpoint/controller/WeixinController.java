package com.viewpoint.controller;

import com.viewpoint.config.ProjectUrlConfig;
import com.viewpoint.constant.CookieConstant;
import com.viewpoint.constant.RedisConstant;
import com.viewpoint.dataobject.User;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.service.UserService;
import com.viewpoint.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RequestMapping("/weixin")
@Controller
@Slf4j
public class WeixinController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) throws UnsupportedEncodingException {
        String url = projectUrlConfig.wechatMpAuthorize + "/weixin/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAuth2Scope.SNSAPI_USERINFO,URLEncoder.encode(returnUrl,"UTF-8"));
        log.info("网页授权获取code,result={}",redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String redirectUrl, HttpServletResponse response){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        User user = new User();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            log.info("微信账号:{}",wxMpUser);
            user.setOpenid(wxMpUser.getOpenId());
            user.setIcon(wxMpUser.getHeadImgUrl());
            user.setAlias(wxMpUser.getNickname());
            user.setName(wxMpUser.getNickname());
            userService.save(user);
        } catch (WxErrorException e) {
            log.error("微信网页授权 {}",e);
            throw new ViewpointException(ResultEnum.WX_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        //3. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, openId, RedisConstant.EXPIRE);
        return "redirect:" + redirectUrl;
    }
}
