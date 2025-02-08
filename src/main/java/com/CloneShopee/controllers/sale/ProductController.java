package com.CloneShopee.controllers.sale;

import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.Shop;
import com.CloneShopee.services.sale.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin("*")
public class ProductController {
	@Autowired
	ProductService productService;

	@GetMapping("/sale/product/findall")
	public ResponseEntity<Object> getProduct(@RequestParam(name = "productName", required = false) String productName,
			@RequestParam(name = "status", required = false) String status) {
		return new ResponseEntity<Object>(
				productService.findProducts(productName, null), HttpStatus.OK);
	}

	@Transactional
	@PutMapping("/sale/product/update/status")
	public ResponseEntity<Object> updateStatusProduct(@RequestParam("status") String status,
			@RequestParam("productId") Integer productId) {
		productService.updateStatusProduct(status, productId, 1);
		return new ResponseEntity<Object>("Update product success", HttpStatus.OK);
	}

	@Transactional
	@PutMapping("/sale/product/update")
	public ResponseEntity<Object> updateProduct(@RequestBody @Valid Product product) {
		productService.updateProperties(product, product.getProperties(), product.getCategory().getId());
		productService.updateProduct(product);
		productService.updateVariantTier(product.getVariantTiers(), product.getProductVariants(), product, true);
		return new ResponseEntity<Object>("Update product  success", HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/sale/product/add")
	public ResponseEntity<Object> insertProduct(@RequestBody @Valid Product product) throws ConstraintException {
		productService.checkVariant(product.getVariantTiers(), product.getProductVariants(), product);
		productService.checkProperties(product.getProperties(), product);
		productService.checkBrand(product.getBrand().getId());
		productService.checkCategory(product.getCategory().getId());
		product.setShop(new Shop(1));
		productService.saveProduct(product);
		return new ResponseEntity<Object>(product.getId(), HttpStatus.OK);
	}

}
