package com.gch.sell.service.impl;

import com.gch.sell.DO.ProductInfo;
import com.gch.sell.repository.ProductInfoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService ;

    @Test
    public void findOne() {
        ProductInfo productInfo = this.productService.findOne("123456") ;
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = this.productService.findUpAll() ;
        assertNotEquals(0,productInfoList.size());
     }

    @Test
    public void findAll() {

    }

    @Test
    public void save() {
    }
}