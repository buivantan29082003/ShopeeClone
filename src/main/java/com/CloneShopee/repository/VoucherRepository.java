package com.CloneShopee.repository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.VoucherShop;

public interface VoucherRepository extends JpaRepository<VoucherShop, Integer> {

    @Query("SELECT p.id FROM VoucherShop p WHERE p.id=:id and p.shop.id=:shopId")
    Optional<Integer> getVoucherByIdAndShopId(@Param("id") Integer id, @Param("shopId") Integer shopId);

    @Query("SELECT p.product.id FROM VoucherShopItem p WHERE p.product.id in:productIds and p.voucher.id=:voucherId")
    Set<Integer> getProductIdsOfVoucher(@Param("productIds") Set<Integer> productIds,
            @Param("voucherId") Integer voucherId);

    @Modifying
    @Query("DELETE VoucherShopItem p where p.product.id in: productIds")
    void deleteVoucherItemInProductIds(@Param("productIds") Set<Integer> productIds);

    @Modifying
    @Query("UPDATE voucherShop p set p.isActive=:isActive where p.id=:id")
    void updateStatusVoucherByVoucherId(@Param("voucherId") Integer voucherId, @Param("isActive") Integer isActive);

    @Modifying
    @Query("UPDATE VoucherShop v SET v.voucherName = :voucherName, v.startDate = :startDate, v.endDate = :endDate, v.voucherType = :voucherType, v.discountValue = :discountValue, v.limitUsage = :limitUsage, v.limitValue = :limitValue, v.isActive = :isActive WHERE v.id = :id")
    int updateVoucherShop(@Param("id") Integer id, @Param("voucherName") String voucherName,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate,
            @Param("voucherType") String voucherType, @Param("discountValue") Double discountValue,
            @Param("limitUsage") Integer limitUsage, @Param("limitValue") Double limitValue,
            @Param("isActive") Integer isActive);

    // @Query("SELECT ")
    // Integer getVoucherIdByVoucherCodeAndShopId(@Param("shopId")Integer shopId);
}
