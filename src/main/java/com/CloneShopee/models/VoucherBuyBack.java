package com.CloneShopee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "voucherbuyback")
public class VoucherBuyBack extends VoucherShop {
    @Min(value = 1, message = "Giá trị mua trung bình từ 0 đến 1")
    @Max(value = 2, message = "Giá trị mua trung bình từ 0 đến 1")
    private Integer countOrderAVG;
    @Min(value = 30, message = "Khoảng thời gian mua từ 30 đến 90 ngày")
    @Max(value = 90, message = "Khoảng thời gian mua từ 30 đến 90 ngày")
    private Integer countDayOrder;

    public VoucherBuyBack(Integer id, Double discountValue, String voucherType, Double minimumPurchase,
            Integer countOrderAVG, Integer countDayOrder) {
        this.setId(id);
        this.setDiscountValue(discountValue);
        this.setVoucherType(voucherType);
        this.setMinimumPurchase(minimumPurchase);
        this.countOrderAVG = countOrderAVG;
        this.countDayOrder = countDayOrder;
    }

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
