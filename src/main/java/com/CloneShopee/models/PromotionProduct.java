package com.CloneShopee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "PromotionProducts")
public class PromotionProduct extends Promotion {
    // @NotNull(message = "disCountValue is require")
    // @Min(value = 1, message = "Min 1 persent")
    // @Max(value = 100, message = "Max 100 persent")
    private Integer disCountValue;
    private Integer quantityRequire;

    public Integer getDisCountValue() {
        return disCountValue;
    }

    public void setDisCountValue(Integer disCountValue) {
        this.disCountValue = disCountValue;
    }

    public Integer getQuantityRequire() {
        return quantityRequire;
    }

    public void setQuantityRequire(Integer quantityRequire) {
        this.quantityRequire = quantityRequire;
    }

}
