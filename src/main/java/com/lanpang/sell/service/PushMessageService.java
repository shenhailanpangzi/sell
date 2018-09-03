package com.lanpang.sell.service;

import com.lanpang.sell.dto.OrderDTO;

/**
 * 推送消息service
 */
public interface PushMessageService {
    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
