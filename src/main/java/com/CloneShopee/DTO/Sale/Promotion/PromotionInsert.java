package com.CloneShopee.DTO.Sale.Promotion;

import java.util.Set;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PromotionInsert<T> {
    @NotNull(message = "Promotion is require")
    @Valid
    private T promotion;
    @NotNull(message = "Products is requrie")
    @Size(min = 1, message = "Limit 1 products")
    private Set<Integer> productIds;

    @NotNull(message = "Product list is require")
    @Size(min = 1, message = "At least one product must be applicable.")
    public T getPromotion() {
        return promotion;
    }

    public void setPromotion(T promotion) {
        this.promotion = promotion;
    }

    public Set<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Integer> productIds) {
        this.productIds = productIds;
    }

}
