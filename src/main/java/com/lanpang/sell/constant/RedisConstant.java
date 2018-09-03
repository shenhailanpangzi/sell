package com.lanpang.sell.constant;

/**
 * redis常量
 * Created by 廖师兄
 * 2017-07-30 16:22
 */
public interface RedisConstant {

    //key的前缀
    String TOKEN_PREFIX = "token_%s";

    //过期时间
    Integer EXPIRE = 7200; //2小时

    //使用用户名登录默认token的值
    String DEFAULT_TOKEN = "default_token";
}
