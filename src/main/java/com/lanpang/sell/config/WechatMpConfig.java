package com.lanpang.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**微信公众号配置
 * Created by 杨浩
 * 2017-07-03 01:25
 */
@Component
public class WechatMpConfig {

    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        //设置Wx Mp配置存储
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }
//    配置作为bean
    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
//       实现类是Wx Mp在内存配置存储中
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
//        设置 APPID
        wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
//      设置  appsecret
        wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());
        return wxMpConfigStorage;
    }
}
