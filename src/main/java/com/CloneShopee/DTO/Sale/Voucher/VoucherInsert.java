package com.CloneShopee.DTO.Sale.Voucher;

import java.util.Set;

import com.CloneShopee.models.VoucherShop;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VoucherInsert {
    @NotNull(message = "Product list is require")
    @Size(min = 1, message = "Ít nhất một sản phẩm áp dụng cho một chương trình voucher")
    Set<Integer> productIds;
    @Valid
    private VoucherShop voucher;

    public Set<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Integer> productIds) {
        this.productIds = productIds;
    }

    public VoucherShop getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherShop voucher) {
        this.voucher = voucher;
    }

}
