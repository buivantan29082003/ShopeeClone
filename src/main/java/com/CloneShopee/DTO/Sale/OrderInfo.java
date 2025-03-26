package com.CloneShopee.DTO.Sale;

import java.util.Date;

import com.CloneShopee.models.Account;
import com.CloneShopee.models.Payment;
import com.CloneShopee.models.Status;
import com.CloneShopee.models.VoucherShop;

public class OrderInfo {
    private Integer id;
    private Date createdDate;
    private Status status;
    private Double totalAmount;
    private String fullAddress;
    private Account account;
    private Payment payment;
    private VoucherShop voucher;

    // Constructor đầy đủ
    public OrderInfo(Integer id, Date createdDate, Status status, Double totalAmount, String fullAddress,
            Integer accountId, String accountFullName, Payment payment) {
        this.id = id;
        this.createdDate = createdDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.fullAddress = fullAddress;
        this.payment = payment;
    }

    // Constructor từ entity Order
    public OrderInfo(com.CloneShopee.models.Order order) {
        this.id = order.getId();
        this.createdDate = order.getCreatedDate();
        this.status = order.getStatus();
        this.totalAmount = order.getTotalAmount();
        this.fullAddress = order.getFullAddress();
        this.account = order.getAccount();
        this.payment = order.getPayment();
        this.voucher = order.getVoucherShop();

    }

    // Getters và Setters
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Payment getPayment() {
        return payment;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public VoucherShop getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherShop voucher) {
        this.voucher = voucher;
    }
}
