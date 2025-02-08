package com.CloneShopee.DTO.Sale.product;

import java.util.List;

import com.CloneShopee.models.Product;
import com.CloneShopee.models.ProductVariant;

public class ProductInfo {
	private Integer id;
	private String productName;
	private List<ProductVariant> productVariants;

	public ProductInfo(Product product) {
		this.id = product.getId();
		this.productName = product.getProductName();
		this.productVariants = product.getProductVariants();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<ProductVariant> getProductVariants() {
		return productVariants;
	}

	public void setProductVariants(List<ProductVariant> productVariants) {
		this.productVariants = productVariants;
	}

}
