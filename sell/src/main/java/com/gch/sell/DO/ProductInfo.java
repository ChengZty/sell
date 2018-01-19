package com.gch.sell.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品信息
 */
@Data
@Entity
public class ProductInfo {

    /** 商品id. */
    @Id
    private String productId ;

    /** 商品名称. */
    private String productName ;

    /** 单价. */
    private BigDecimal productPrice ;

    /** 商品库存. */
    private Integer productStock ;

    /** 商品描述. */
    private String productDescription ;

    /** 商品小图. */
    private String productIcon ;

    /** 商品状态 0-正常 1-下架. */
    private Integer productStatus ;

    /** 类目编号. */
    private Integer categoryType ;

    /** createTime和updateTime两个属性暂时没有用到可以不写,写了记得加上 @DynamicUpdate 让updateTime的值由数据库动态生成 */

    public ProductInfo() {
    }
}
