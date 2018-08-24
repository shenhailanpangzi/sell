package com.lanpang.sell.controller;

import com.lanpang.sell.enums.ResultEnum;
import com.lanpang.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * Created by 杨浩
 * 2017-07-03 01:20
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     *获取code  http://an66uw.natappfree.cc/sell/wechat/authorize?returnUrl=www.baidu.com
     * @param returnUrl
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        //1. 配置
        //2. 调用方法
        String url = "http://34tv86.natappfree.cc/" + "sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));

        log.info("【微信网页授权】获取code,result={}",redirectUrl);
        return "redirect:" + redirectUrl;
    }

    /**
     * 使用code换取openID并且重定向
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();
        System.out.println("默认returnUrl："+returnUrl);
        returnUrl = "http://192.168.43.140/#/";//虚拟机 地址
        System.out.println("【微信网页授权openId】"+"redirect:" + returnUrl + "?openid=" + openId);
        return "redirect:" + returnUrl + "?openid=" + openId;
    }

}
