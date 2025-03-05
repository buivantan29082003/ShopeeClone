package com.CloneShopee.DTO.User;

import jakarta.validation.constraints.NotNull;

public class ShopItemDTO {
    @NotNull(message = "Không được để trống shopId")
    Integer shopId;
    Integer voucherId;
    // @NotNull(message = "Chưa có tokenShip")
    private TokenShip tokenShip;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public TokenShip getTokenShip() {
        return tokenShip;
    }

    public void setTokenShip(TokenShip tokenShip) {
        this.tokenShip = tokenShip;
    }

}
