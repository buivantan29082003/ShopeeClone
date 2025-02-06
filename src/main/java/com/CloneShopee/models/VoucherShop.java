package com.CloneShopee.models;

import java.util.Date;

public class VoucherShop {
	private Integer id;
	private String vouhcherName;
	private String voucherCode;
	private Date startDate;
	private Date endDate;
	private Integer voucherType;
	private Double discountValue;
	private Integer limitUsage;
	private Double limitValue;
	private Integer isActive;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVouhcherName() {
		return vouhcherName;
	}
	public void setVouhcherName(String vouhcherName) {
		this.vouhcherName = vouhcherName;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(Integer voucherType) {
		this.voucherType = voucherType;
	}
	public Double getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}
	public Integer getLimitUsage() {
		return limitUsage;
	}
	public void setLimitUsage(Integer limitUsage) {
		this.limitUsage = limitUsage;
	}
	public Double getLimitValue() {
		return limitValue;
	}
	public void setLimitValue(Double limitValue) {
		this.limitValue = limitValue;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	
}
