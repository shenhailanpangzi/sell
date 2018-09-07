package com.lanpang.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @program: mysell
 * @description:
 * @author: yanghao
 * @create: 2018-09-03 16:00
 **/
@Component
@Slf4j
public class RedisLock {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁 （redis分布式锁）
     * @param key productId - 商品的唯一标志
     * @param value  当前时间+超时时间
     * @return
     */
    public boolean lock(String key,String value){
//        1、使用判断setnx判断是否以前有值，没有则set并返回true，如果以前有值则判断是否死锁
//        2、如果锁过期了，则获取原来的时间并且将新时间set
//        3、使用redis的getAndSet 改变value 根据条件判断 只允许第一个访问redis的线程获得该锁

        if(stringRedisTemplate.opsForValue().setIfAbsent(key,value)){//对应setnx命令
            //可以成功设置,也就是key不存在
            return true;
        }
        //判断锁超时 - 防止原来的操作异常，没有运行解锁操作  防止死锁
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        //如果锁过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){//currentValue不为空且小于当前时间
            //获取上一个锁的时间value
            String oldValue =stringRedisTemplate.opsForValue().getAndSet(key,value);//对应getset，如果key存在

            //假设之前currentValue = A   两个线程的目前的value都是B，并且超时则会出现两个线程同时返回true，也就是同时获得一把锁
            //这里是判断getAndSet之后是否oldValue已经被修改，如果未被修改（currentValue=oldValue=A），则当前线程获得锁，之后的线程由于oldValue被修改为B，获取锁失败
            //而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的value已经变成了B。只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue) ){
                //oldValue不为空且oldValue等于currentValue，也就是校验是不是上个对应的商品时间戳，也是防止并发
                return true;
            }
        }
        return false;
    }


    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key,String value){
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value) ){
                stringRedisTemplate.opsForValue().getOperations().delete(key);//删除key
            }
        } catch (Exception e) {
            log.error("[Redis分布式锁] 解锁出现异常了，{}",e);
        }
    }

}