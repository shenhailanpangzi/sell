package com.lanpang.sell.dataobject.mapper;

import com.lanpang.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("category_name","店长推荐");
        map.put("category_Type","5");
        int i = mapper.insertByMap(map);
        Assert.assertEquals(1,i);
    }
    @Test
    public void insertByObject() {
        ProductCategory category = new ProductCategory();
        category.setCategoryType(6);
        category.setCategoryName("店长推荐2");
        int i = mapper.insertByObject(category);
        Assert.assertEquals(1,i);
    }
    @Test
    public void findByCategoryType() {
        ProductCategory category = mapper.findByCategoryType(5);
        System.out.println(category);
        Assert.assertNotNull(category);
    }
    @Test
    public void findByCategoryName() {
        List<ProductCategory> categorys = mapper.findByCategoryName("店长");
        System.out.println(categorys);
        Assert.assertNotEquals(0,categorys.size());
    }

    @Test
    public void updateByCategoryType() {
        int num = mapper.updateByCategoryType("店长么么哒", 6);
        System.out.println(num);
        Assert.assertNotEquals(1,num);
    }

    @Test
    public void updateByObject() {
        ProductCategory category = new ProductCategory();
        category.setCategoryType(6);
        category.setCategoryName("店长推荐2");
        int num = mapper.updateByObject(category);
        System.out.println(num);
        Assert.assertEquals(1,num);
    }
    @Test
    public void deleteByCategoryType() {

        int num = mapper.deleteByCategoryType(6);
        Assert.assertEquals(1,num);
    }
    @Test
    public void selectByCategoryType() {
        ProductCategory category = mapper.selectByCategoryType(5);
        System.out.println(category);
        Assert.assertNotNull(category);
    }
}