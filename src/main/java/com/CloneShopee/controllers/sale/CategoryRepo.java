package com.CloneShopee.controllers.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.services.sale.CategorySerice;

@RestController
public class CategoryRepo {

    @Autowired
    CategorySerice categoryService;

    @GetMapping("sale/categories/getall")
    public ResponseEntity<Object> getAllCategories() {
        System.out.println("Hello World");
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }
}
