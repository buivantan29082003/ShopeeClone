package com.CloneShopee.DTO.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface CartInfoDTO {
    Integer getQuantity();

    Integer getProductId();

    String getProductName();

    Integer getVariantId();

    String getImage();

    String getVariantName();

    Double getPrice();

    @JsonIgnore
    Integer getShopId();

    @JsonIgnore
    String getShopName();
}
