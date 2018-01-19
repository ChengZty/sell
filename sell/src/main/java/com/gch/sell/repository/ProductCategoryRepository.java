package com.gch.sell.repository;

import com.gch.sell.DO.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 类目repository
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory>  findByCategoryTypeIn(List<Integer> categoryTypeList) ;
}
