package com.CloneShopee.controllers.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.DTO.User.ProductSearch;
import com.CloneShopee.DTO.User.PromotionInProductSearch;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.Product;
import com.CloneShopee.services.sale.ProductService;

@RestController
@CrossOrigin("*")
public class ProductUserController {

    @Autowired
    ProductService productService;

    @GetMapping("user/product/getall")
    public ResponseEntity<Object> getAllProducts(
            @RequestParam(name = "productId", defaultValue = "-1") Integer productId) {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/user/product/filter")
    public ResponseEntity<Object> filterProduct(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "categoryIds", required = false) List<Integer> categoryIds,
            @RequestParam(value = "brandIds", required = false) List<Integer> brandIds) {
        List<Product> products = productService.filterProducts(name, categoryIds, status, brandIds, minPrice, maxPrice);
        List<ProductSearch> productSearchs = products.stream().map(v -> new ProductSearch(v.getId(), v.getShop(),
                v.getProductName(), v.getMinPrice(), v.getMaxPrice(), v.getProductImage())).toList();
        List<PromotionInProductSearch> promotions = productService.getPromotionInfoOfProduct(products);
        Map<Integer, List<PromotionInProductSearch>> promotionMap = new HashMap<>();
        for (PromotionInProductSearch promo : promotions) {
            promotionMap.computeIfAbsent(promo.getProductId(), k -> new ArrayList<>()).add(promo);
        }
        for (ProductSearch product : productSearchs) {
            product.setPromotions(promotionMap.getOrDefault(product.getId(), Collections.emptyList()));
        }
        return new ResponseEntity<>(new BaseRespone(productSearchs, "success"), HttpStatus.OK);
    }

    @GetMapping("/user/product/detail")
    public ResponseEntity<Object> getInffoDetail(
            @RequestParam(name = "productId", required = false) Integer productId) {
        Product product = productService.findByIdFullProperties(productId);
        if (product != null) {
            Map<String, Object> productInfo = new HashMap<>();
            productInfo.put("product", product);
            productInfo.put("promotion", productService.getPromotionOfProductId(productId));
            return new ResponseEntity(new BaseRespone(productInfo, "success"), HttpStatus.OK);
        }
        return new ResponseEntity(new BaseRespone(null, "Not find product"), HttpStatus.OK);
    }

}
