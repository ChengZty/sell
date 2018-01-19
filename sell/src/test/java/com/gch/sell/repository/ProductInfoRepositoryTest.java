package com.gch.sell.repository;

import com.gch.sell.DO.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository ;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo() ;
        productInfo.setProductId("123456");
        productInfo.setProductName("五谷鱼粉");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(111);
        productInfo.setProductDescription("便宜又好吃");
        productInfo.setCategoryType(2);
        productInfo.setProductIcon("http://adbc.jpg");
        productInfo.setProductStatus(0);

        ProductInfo result = this.repository.save(productInfo) ;
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = this.repository.findByProductStatus(0) ;
        Assert.assertNotEquals(0, productInfoList.size());
    }
}