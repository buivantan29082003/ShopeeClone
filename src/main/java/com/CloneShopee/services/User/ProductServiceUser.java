package com.CloneShopee.services.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.repository.ProductRepository;

@Service
public class ProductServiceUser {

    @Autowired
    ProductRepository productRepo;

    public Boolean isSameProduct(Integer productId1, Integer productId2, Integer productId) {
        return productRepo.checkVariantSameProduct(productId1, productId2, productId) == 2;
    }
}
