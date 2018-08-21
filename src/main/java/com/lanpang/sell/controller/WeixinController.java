package com.lanpang.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 廖师兄
 * 2017-07-03 00:50
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code) {
        System.out.println("进入auth方法。。。");
        log.info("进入auth方法。。。");
        log.info("code={}", code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=wx135faac6ebc8c2eb&" +
                "secret=b4163d745a68245ab34924b65722ff09&" +
                "code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello!!";
    }
}
