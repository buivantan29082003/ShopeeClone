package com.CloneShopee.models;

public class PromotionItem {
	private Integer id;
	private Promotion promotion;
	private Integer startQuantity;
	private Integer endQuantity;
	private Integer isConstraint;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Promotion getPromotion() {
		return promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	public Integer getStartQuantity() {
		return startQuantity;
	}
	public void setStartQuantity(Integer startQuantity) {
		this.startQuantity = startQuantity;
	}
	public Integer getEndQuantity() {
		return endQuantity;
	}
	public void setEndQuantity(Integer endQuantity) {
		this.endQuantity = endQuantity;
	}
	public Integer getIsConstraint() {
		return isConstraint;
	}
	public void setIsConstraint(Integer isConstraint) {
		this.isConstraint = isConstraint;
	}
	
}
