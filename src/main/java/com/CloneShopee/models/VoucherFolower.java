package com.CloneShopee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "voucherfolower")
public class VoucherFolower extends VoucherShop {
    private Integer countDayFolower;

    public Integer getCountDayFolower() {
        return countDayFolower;
    }

    public void setCountDayFolower(Integer countDayFolower) {
        this.countDayFolower = countDayFolower;
    }

}
