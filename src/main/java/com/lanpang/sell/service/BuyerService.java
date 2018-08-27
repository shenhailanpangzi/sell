package com.lanpang.sell.service;


import com.lanpang.sell.dto.OrderDTO;

/**
 * 买家
 * Created by 杨浩
 * 2018-06-22 00:11
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
