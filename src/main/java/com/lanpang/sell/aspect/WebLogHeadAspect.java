package com.lanpang.sell.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description：Web层日志切面。
 * 日志保存对页面所有C层请求进行方法拦截,并记录日志
 * @Author:yanghao
 * @Date：2018/4/10 21:01
 */
@Aspect
@Component
public class WebLogHeadAspect {

    @Pointcut("execution(public * com.lanpang.sell.controller.*Controller.*(..))")
    public void webLog() {
    }
    @Around("webLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url =request.getRequestURI();
        System.err.println("请求url:{"+url+"}");
        Object[] req = pjp.getArgs();
//        for (int i = 0; i < req.length; i++) {
//            String reqjson = JSON.toJSONString(req[i]);
//            System.err.println("第"+i+"个参数:"+reqjson);
//        }
        Object o = pjp.proceed();
        System.out.println("反参："+o);
        return o;
    }
//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) {
//
//        System.out.println("入参："+joinPoint.getArgs());
//    }
}
