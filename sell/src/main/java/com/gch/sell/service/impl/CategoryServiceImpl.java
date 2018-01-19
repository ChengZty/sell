package com.gch.sell.service.impl;

import com.gch.sell.DO.ProductCategory;
import com.gch.sell.repository.ProductCategoryRepository;
import com.gch.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository ;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return this.productCategoryRepository.findOne(categoryId) ;
    }

    @Override
    public List<ProductCategory> findAll() {
        return this.productCategoryRepository.findAll() ;
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return this.productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return this.productCategoryRepository.save(productCategory);
    }
}
