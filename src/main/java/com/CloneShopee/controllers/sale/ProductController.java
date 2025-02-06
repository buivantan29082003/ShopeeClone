package com.CloneShopee.controllers.sale;

import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.DTO.Sale.product.ProductDTOUpdate;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.Shop;
import com.CloneShopee.repository.ProductRepository;
import com.CloneShopee.services.sale.ProductService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; 


@RestController
@CrossOrigin("*")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@GetMapping("/hello")
	public ResponseEntity<Object> getProduct( ) {
		return new ResponseEntity<Object>(productService.getProductById(1),HttpStatus.OK);
	}
	
	@Transactional
	@PutMapping("/sale/product/update") 
	public ResponseEntity<Object> updateProduct(@RequestBody @Valid Product product ){  
		productService.updateProperties(product, product.getProperties(),product.getCategory().getId());
		productService.updateProduct(product);
		return new ResponseEntity<Object>("Update product  success",HttpStatus.OK);
	}
	
	
	@Transactional
	@PostMapping("/sale/product/add")
	public ResponseEntity<Object> insertProduct(@RequestBody @Valid Product product ) throws ConstraintException {  
		productService.checkVariant(product.getVariantTiers(),product.getProductVariants(), product);
		productService.checkProperties(product.getProperties(), product);
		productService.checkBrand(product.getBrand().getId());
		productService.checkCategory(product.getCategory().getId());
		product.setShop(new Shop(1));
		productService.saveProduct(product);
		return new ResponseEntity<Object>(product.getId(),HttpStatus.OK);
	}
	
	
	

	
}
