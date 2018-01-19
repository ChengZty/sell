package com.gch.sell.service.impl;

import com.gch.sell.DO.ProductInfo;
import com.gch.sell.enums.ProductStatusEnum;
import com.gch.sell.repository.ProductInfoRepository;
import com.gch.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository ;

    @Override
    public ProductInfo findOne(String productId) {
        return this.productInfoRepository.findOne(productId) ;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return this.productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return this.productInfoRepository.findAll(pageable) ;

    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return null;
    }
}
