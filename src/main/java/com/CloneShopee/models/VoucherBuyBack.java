package com.CloneShopee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "voucherbuyback")
public class VoucherBuyBack extends VoucherShop {
    private Integer countOrderAVG;
    private Integer countDayOrder;

    public Integer getCountDayAVG() {
        return countOrderAVG;
    }

    public void setCountDayAVG(Integer countDayAVG) {
        this.countOrderAVG = countDayAVG;
    }

    public Integer getCountDayOrder() {
        return countDayOrder;
    }

    public void setCountDayOrder(Integer countDayOrder) {
        this.countDayOrder = countDayOrder;
    }

}
