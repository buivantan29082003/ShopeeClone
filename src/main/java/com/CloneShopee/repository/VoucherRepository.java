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

        // @Query("SELECT p.id FROM VoucherShop p WHERE p.id=:voucherId and
        // p.shop.id=:shopId")
        // Optional<Integer> getVoucherInfoForCaculateOrder(@Param("voucherId") Integer
        // voucherId);

        @Query("SELECT p.id FROM VoucherShop p WHERE p.id=:id and p.shop.id=:shopId")
        Optional<Integer> getVoucherByIdAndShopId(@Param("id") Integer id, @Param("shopId") Integer shopId);

        @Modifying
        @Query("DELETE VoucherShopItem p where p.product.id in: productIds")
        void deleteVoucherItemInProductIds(@Param("productIds") Set<Integer> productIds);

        @Modifying
        @Query("UPDATE VoucherShop p set p.isActive=:isActive where p.id=:id")
        void updateStatusVoucherByVoucherId(@Param("voucherId") Integer voucherId,
                        @Param("isActive") Integer isActive);

        @Modifying
        @Query("UPDATE VoucherShop v SET v.voucherName = :voucherName, v.startDate = :startDate, v.endDate = :endDate, v.voucherType = :voucherType, v.discountValue = :discountValue, v.limitUsage = :limitUsage, v.limitValue = :limitValue, v.isActive = :isActive WHERE v.id = :id")
        int updateVoucherShop(@Param("id") Integer id, @Param("voucherName") String voucherName,
                        @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        @Param("voucherType") String voucherType, @Param("discountValue") Double discountValue,
                        @Param("limitUsage") Integer limitUsage, @Param("limitValue") Double limitValue,
                        @Param("isActive") Integer isActive);

        // @Query("SELECT ")
        // Integer getVoucherIdByVoucherCodeAndShopId(@Param("shopId")Integer shopId);

        @Query("""
                        SELECT new com.CloneShopee.models.VoucherShop(vs.id, vs.discountValue, vs.voucherType,vs.minimumPurchase)
                        FROM VoucherShop vs
                        JOIN VoucherShopAccount vc
                            ON vc.id.voucherId = vs.id
                        WHERE vs.id = :voucherId
                          AND vc.id.accountId = :accountId
                          AND vs.shop.id = :shopId and vc.quantityUsed<vs.limitUsage AND now() between vs.startDate and vs.endDate
                        """)
        public Optional<VoucherShop> getVoucherShopIsApply(
                        @Param("voucherId") Integer voucherId,
                        @Param("shopId") Integer shopId,
                        @Param("accountId") Integer userId);

        @Modifying
        @Query("UPDATE VoucherShopAccount vc SET vc.quantityUsed=vc.quantityUsed+1 where vc.id.voucherId=:voucherId and vc.id.accountId=:accountId ")
        public Integer updateQuantityUsed(@Param("voucherId") Integer voucherId, @Param("accountId") Integer accountId);

}
