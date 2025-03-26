package com.CloneShopee.DTO.Sale;

import java.util.Date;

import com.CloneShopee.models.Account;
import com.CloneShopee.models.Payment;
import com.CloneShopee.models.Status;

public class OrderInfo {
    private Integer id;
    private Date createdDate;
    private Status status;
    private Double totalAmount;
    private String fullAddress;
    private Integer accountId;
    private String accountFullName;
    private Payment payment;

    // Constructor đầy đủ
    public OrderInfo(Integer id, Date createdDate, Status status, Double totalAmount, String fullAddress,
            Integer accountId, String accountFullName, Payment payment) {
        this.id = id;
        this.createdDate = createdDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.fullAddress = fullAddress;
        this.accountId = accountId;
        this.accountFullName = accountFullName;
        this.payment = payment;
    }

    // Constructor từ entity Order
    public OrderInfo(com.CloneShopee.models.Order order) {
        this.id = order.getId();
        this.createdDate = order.getCreatedDate();
        this.status = order.getStatus();
        this.totalAmount = order.getTotalAmount();
        this.fullAddress = order.getFullAddress();
        this.accountId = order.getAccount().getId();
        this.accountFullName = order.getAccount().getFullName();
        this.payment = order.getPayment();
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountFullName() {
        return accountFullName;
    }

    public void setAccountFullName(String accountFullName) {
        this.accountFullName = accountFullName;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
