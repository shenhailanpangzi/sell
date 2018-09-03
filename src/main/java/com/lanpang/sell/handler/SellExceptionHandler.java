package com.lanpang.sell.handler;

import com.lanpang.sell.VO.ResultVO;
import com.lanpang.sell.config.ProjectUrlConfig;
import com.lanpang.sell.exception.ResponseBankException;
import com.lanpang.sell.exception.SellException;
import com.lanpang.sell.exception.SellerAuthorizeException;
import com.lanpang.sell.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/** 捕获登录异常 并且跳转
 * Created by 杨浩
 * 2017-07-30 17:44
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //捕获 拦截微信登录异常 自动跳转页面
    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        //判断登录方式
        if (projectUrlConfig.getLoginsource().equals("wechat")){
            return new ModelAndView("redirect:"
                    .concat(projectUrlConfig.getWechatOpenAuthorize())
                    .concat("/sell/wechat/qrAuthorize")
                    .concat("?returnUrl=")
                    .concat(projectUrlConfig.getSell())
                    .concat("/sell/seller/login"));
        }else {
            return new ModelAndView("/common/login");
        }
    }

    /**
     * @ResponseBody是作用在方法上的，@ResponseBody 表示该方法的返回结果直接写入 HTTP response body 中，
     * 一般在异步获取数据时使用【也就是AJAX】，在使用 @RequestMapping后，返回值通常解析为跳转路径，但是加上
     * @ResponseBody 后返回结果不会被解析为跳转路径，而是直接写入 HTTP response body 中。
     * 比如异步获取 json 数据，加上 @ResponseBody 后，会直接返回 json 数据。
     * @RequestBody是作用在形参列表上，用于将前台发送过来固定格式的数据【xml 格式或者 json等】封装为对应的 JavaBean 对象，封装时使用到的一个对象是系统默认配置的 HttpMessageConverter进行解析，然后封装到形参上。
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = SellException.class)
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    /**@ResponseStatus注解用于指定返回的http错误码
     *
     * @return
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ResponseBankException.class)
    public ModelAndView ResponseBankException(){
        //判断登录方式
        if (projectUrlConfig.getLoginsource().equals("wechat")){
            return new ModelAndView("redirect:"
                    .concat(projectUrlConfig.getWechatOpenAuthorize())
                    .concat("/sell/wechat/qrAuthorize")
                    .concat("?returnUrl=")
                    .concat(projectUrlConfig.getSell())
                    .concat("/sell/seller/login"));
        }else {
            return new ModelAndView("/common/login");
        }
    }
}
