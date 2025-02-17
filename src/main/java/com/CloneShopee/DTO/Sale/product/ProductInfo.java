package com.CloneShopee.DTO.Sale.product;

import java.util.ArrayList;
import java.util.List;

import com.CloneShopee.models.Product;
import com.CloneShopee.models.ProductVariant;
import com.CloneShopee.models.PromotionItem;

public class ProductInfo {
	private Integer id;
	private String productName;
	private List<ProductVariant> productVariants;
	private Double minPrice;
	private Double maxPrice;
	private String productImage;
	private List<PromotionItem> PromotionItems;

	public ProductInfo(Product product) {
		this.id = product.getId();
		this.productName = product.getProductName();
		this.minPrice = product.getMinPrice();
		this.maxPrice = product.getMaxPrice();
		this.productImage = product.getProductImage();

	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
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

	public List<PromotionItem> getPromotionItems() {
		return PromotionItems;
	}

	public void setPromotionItems(List<PromotionItem> promotionItems) {
		PromotionItems = promotionItems;
	}

}
