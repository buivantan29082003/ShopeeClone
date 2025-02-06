package com.CloneShopee.models;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Order {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date createdDate;
	private Account account;
	private VoucherShop voucherShop;
	private Double totalAmount;
	private String note;
	private Double shipFee;
	private Integer fullAddress;
	private String reasonCancel;
	private VoucherPlatfom voucherPlatfom;
	private Shop shop;
	private Payment payment;
	private Status status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public VoucherShop getVoucherShop() {
		return voucherShop;
	}
	public void setVoucherShop(VoucherShop voucherShop) {
		this.voucherShop = voucherShop;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Double getShipFee() {
		return shipFee;
	}
	public void setShipFee(Double shipFee) {
		this.shipFee = shipFee;
	}
	public Integer getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(Integer fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getReasonCancel() {
		return reasonCancel;
	}
	public void setReasonCancel(String reasonCancel) {
		this.reasonCancel = reasonCancel;
	}
	public VoucherPlatfom getVoucherPlatfom() {
		return voucherPlatfom;
	}
	public void setVoucherPlatfom(VoucherPlatfom voucherPlatfom) {
		this.voucherPlatfom = voucherPlatfom;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
