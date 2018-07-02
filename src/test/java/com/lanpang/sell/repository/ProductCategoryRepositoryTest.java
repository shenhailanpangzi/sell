package com.lanpang.sell.repository;

import com.lanpang.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * @Description：
 * @Author:yanghao
 * @Date：2018/6/5 16:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        System.out.println(repository.findAll());
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional//这个注解是测试通过与否 完全回滚事务
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("女生最爱", 4);
//        判读一个对象是否非空(非空)。
        ProductCategory result = repository.save(productCategory);
        System.out.println(result);
//        使用断言 判断是否相等，可以指定输出错误信息。
        Assert.assertNotNull(result);
//        使用断言 第一个参数是期望值，第二个参数是实际的值。
//        Assert.assertNotEquals(null, result);
    }
    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(1,2,4);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
        System.err.println(result);
    }

}