package com.lanpang.sell.service.impl;

import com.lanpang.sell.dto.OrderDTO;
import com.lanpang.sell.service.OrderService;
import com.lanpang.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * @Description：PayService的测试类
 * @Author:yanghao
 * @Date：2018/8/23 10:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;
    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("1534230869625336868");

        payService.create(orderDTO);
    }

    /**
     * 微信退款
     */
    @Test
    public void refund() {
        OrderDTO orderDTO = orderService.findOne("1534230869625336868");
        payService.refund(orderDTO);
    }

}