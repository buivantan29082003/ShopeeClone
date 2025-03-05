package com.CloneShopee.controllers.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.repository.ProductVariantRepository;

@RestController
@CrossOrigin("*")
public class ProductUserController {
    @Autowired
    ProductVariantRepository productVariantRepo;

    @GetMapping("user/product/variantsameproduct")
    public ResponseEntity<Object> getVariantSameProduct(
            @RequestParam(name = "productId", defaultValue = "-1") Integer productId) {
        return new ResponseEntity<>(productVariantRepo.findByProductId(productId), HttpStatus.OK);
    }
}
