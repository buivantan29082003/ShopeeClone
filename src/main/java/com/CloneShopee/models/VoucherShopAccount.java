package com.CloneShopee.models;

public class VoucherShopAccount {
	private Integer id;
	private VoucherShop voucher;
	private Account account;
	private Integer countUsed;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public VoucherShop getVoucher() {
		return voucher;
	}
	public void setVoucher(VoucherShop voucher) {
		this.voucher = voucher;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Integer getCountUsed() {
		return countUsed;
	}
	public void setCountUsed(Integer countUsed) {
		this.countUsed = countUsed;
	}
	
}
