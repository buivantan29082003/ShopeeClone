package com.CloneShopee.DTO.Sale;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface OrderItemDTO {
    Integer getQuantity();

    Double getPrice();

    String getProductImage();

    String getVariantName();

    String getProductName();

    Integer getVariantId();

    Integer getProductId();

    @JsonIgnore
    Integer getOrderId();
}