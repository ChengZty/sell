package com.gch.sell.repository;

import com.gch.sell.DO.ProductCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    private ProductCategory productCategory = null ;

    @Autowired
    private ProductCategoryRepository repository ;

    @Before
    public void testBefore(){

    }

    @Test
    public void findOneTest(){
        this.productCategory = this.repository.findOne(1) ;
        Assert.assertNotNull(productCategory);
    }

    @Test
    @Transactional /** 在测试中加事务一定是回滚的 有时候我们不希望test时的垃圾数据进入数据库 */
    public void saveTest(){
        this.productCategory = new ProductCategory() ;
        this.productCategory.setCategoryName("男生爆款");
        this.productCategory.setCategoryType(3);
        this.productCategory = this.repository.save(this.productCategory) ;
        Assert.assertNotNull(this.productCategory);
    }

    @Test
    /**
     * 当数据库中的时间类型为timestamp而且设置了更新时设为当前时间(on update current_timestamp)
     * 如果对应的实体中的updateTime有值就数据库就不会自动创建当前时间,而是使用实体中的值
     * 为了解决这个问题,可以使用一个注解 不管实体中对应的属性有没有值,都让数据库知道自动更新
     * @DynamicUpdate
     */
    public void updateTest() {
        this.productCategory = this.repository.findOne(2)  ;
//        this.productCategory = new ProductCategory() ;
        this.productCategory.setCategoryId(2);
        this.productCategory.setCategoryName("女生爆款");
        this.productCategory.setCategoryType(666);
        this.repository.save(this.productCategory) ;
    }


    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> categoryTypeList = Arrays.asList(1,2, 3,4);
        List<ProductCategory> productCategoryList = this.repository.findByCategoryTypeIn(categoryTypeList) ;
        Assert.assertNotEquals(0, productCategoryList.size());
    }
}