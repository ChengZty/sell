package com.gch.sell.repository;

import com.gch.sell.DO.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    /**
     * 根据商品状态查询商品信息
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus) ;
}
