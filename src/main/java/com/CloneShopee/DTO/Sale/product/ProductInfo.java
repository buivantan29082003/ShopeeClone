package com.CloneShopee.DTO.Sale.product;

import java.util.List;

import com.CloneShopee.models.VariantTier;

public class ProductInfo {
	private Integer id;
	private String productName;
	private String brandName;
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	private List<VariantTier> variantTiers;
	public List<VariantTier> getVariantTiers() {
		return variantTiers;
	}
	public void setVariantTiers(List<VariantTier> variantTiers) {
		this.variantTiers = variantTiers;
	}
	public ProductInfo(Integer id,String productName,List<VariantTier>l) {
		this.id=id;
		this.variantTiers=l;
		this.productName=productName;
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
}
