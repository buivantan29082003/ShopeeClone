package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.CloneShopee.DTO.Sale.product.ProductInfo;
import com.CloneShopee.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

//    @Query("SELECT new com.CloneShopee.DTO.Sale.product.ProductInfo(p.id, p.productName,p.variantTiers) FROM Product p")
//    List<ProductInfo> findAllProduct();
}

