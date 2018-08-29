package com.lanpang.sell.service.impl;

import com.lanpang.sell.dataobject.ProductInfo;
import com.lanpang.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description：商品service
 * @Author:yanghao
 * @Date：2018/6/6 16:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productService.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    /**
     * 分页查询所有商品
     * @throws Exception
     */
    @Test
    public void findAll() throws Exception {
        PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        System.err.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    /**
     * 上传商品
     * @throws Exception
     */
    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
    /**
     * 下架商品
     * @throws Exception
     */
    @Test
    public void onSale() throws Exception {
        ProductInfo result = productService.onSale(String.valueOf(123456));
        Assert.assertEquals(ProductStatusEnum.UP,result.getProductStatusEnum());
    }
    /**
     * 下架商品
     * @throws Exception
     */
    @Test
    public void offSale() throws Exception {
        ProductInfo result = productService.offSale(String.valueOf(123456));
        Assert.assertEquals(ProductStatusEnum.DOWN,result.getProductStatusEnum());
    }
}