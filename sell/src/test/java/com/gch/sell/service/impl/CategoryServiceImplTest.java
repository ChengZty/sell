package com.gch.sell.service.impl;

import com.gch.sell.DO.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService ;

    @Test
    public void findOne() {
        ProductCategory productCategory = this.categoryService.findOne(1) ;
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = this.categoryService.findAll() ;
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = this.categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3)) ;
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    @Transactional /** 测试中事务一定会回滚 防止添加test时的垃圾数据 */
    public void save() {
        ProductCategory productCategory = new ProductCategory(5,"bbb") ;
        this.categoryService.save(productCategory) ;
    }
}