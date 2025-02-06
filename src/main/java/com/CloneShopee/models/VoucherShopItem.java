package com.CloneShopee.models;

public class VoucherShopItem {
	private Integer id;
	private VoucherShop voucherShop;
	private Product product;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public VoucherShop getVoucherShop() {
		return voucherShop;
	}
	public void setVoucherShop(VoucherShop voucherShop) {
		this.voucherShop = voucherShop;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
