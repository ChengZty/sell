package com.gch.sell.DO;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 商品类目
 * 使用 lombok 注解@data
 * spring-data-jpa帮我们将驼峰转化成下划线,
 * 所以如果命名规范是可以省去 @Table(name="product_category") @Column(name="category_id")
 *
 * 数据库中updateTime设置了更新时自动更新为当前时间(on update current_timestamp)
 * 如果实体中的updateTime不为null也就是有值, 数据库就不会自动更新, 会将实体中的值设置进去
 * 为了解决在这种情况下不自动更新的问题我们可以加上 @DynamicUpdate
 */

@Data
@Entity
@DynamicUpdate
public class ProductCategory {

    /** 类目id. */
    @Id
    @GeneratedValue
    private Integer categoryId ;

    /** 类目编号. */
    private Integer categoryType ;

    /** 类目名字. */
    private String categoryName ;

    /** 创建时间. */
    private Date createTime ;

    /** 更新时间. */
    private Date updateTime ;

    /**
     * 很多框架在使用我们这些实体类创建对象时都会调用无参构造器
     * 就像所以我们在添加有参构造器时一定要加上无参的,要不然就会报错
     */
    public ProductCategory() {
    }

    public ProductCategory(Integer categoryType, String categoryName) {
        this.categoryType = categoryType;
        this.categoryName = categoryName;
    }
}
