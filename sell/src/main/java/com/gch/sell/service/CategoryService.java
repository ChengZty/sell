package com.gch.sell.service;

import com.gch.sell.DO.ProductCategory;

import java.util.List;

/**
 * 类目service
 */
public interface CategoryService {

    ProductCategory findOne(Integer CategoryId) ;

    List<ProductCategory> findAll() ;

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) ;

    ProductCategory save(ProductCategory productCategory) ;
}
