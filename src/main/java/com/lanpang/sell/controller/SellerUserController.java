package com.lanpang.sell.controller;


import com.lanpang.sell.config.ProjectUrlConfig;
import com.lanpang.sell.constant.CookieConstant;
import com.lanpang.sell.constant.RedisConstant;
import com.lanpang.sell.dataobject.SellerInfo;
import com.lanpang.sell.enums.ResultEnum;
import com.lanpang.sell.repository.SellerInfoRepository;
import com.lanpang.sell.service.SellerService;
import com.lanpang.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 * Created by 廖师兄
 * 2017-07-30 15:28
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private SellerInfoRepository sellerInfoRepository;


    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {

        //1. openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        //2. 设置token至redis
        String token = UUID.randomUUID().toString();
        //过期时间
        Integer expire = RedisConstant.EXPIRE;
                 //opsForValue操作某些value
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        //3. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");

    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3. 清除cookie  设置过期时间为0
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/logindex")
    public ModelAndView logindex(){
        return new ModelAndView("/common/login");
    }
    /**
     * 账号密码登录验证
     * @param map
     * @return
     */
    @PostMapping("/userlogin")
    public ModelAndView logindex(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpServletResponse response,
                                 Map<String, Object> map) {
//        1、查询数据库账号密码匹配
        SellerInfo sellerInfo = sellerInfoRepository.findByUsernameAndPassword(username, password);
//        2、不匹配则返回登录页面
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/logindex");
            return new ModelAndView("common/error");
        }
//        3、如果匹配则设置token 并存入redis
        String token = UUID.randomUUID().toString();
        //过期时间
        Integer expire = RedisConstant.EXPIRE;
        //opsForValue操作某些value
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token),RedisConstant.DEFAULT_TOKEN, expire, TimeUnit.SECONDS);

        //3. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        map.put("msg", "登陆成功！！！");
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }
}
