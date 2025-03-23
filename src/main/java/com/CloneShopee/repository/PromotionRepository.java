package com.CloneShopee.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CloneShopee.DTO.User.PromotionInProductSearch;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.Promotion;
import com.CloneShopee.models.PromotionComboOption;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

        // SELECT
        // ========================================================================================================

        // @Query("SELECT new String() FROM PromotionItem pi ")
        // List<String> getPromotionOfProduct(@Param("products") List<Product>
        // products);

        @Query("SELECT new com.CloneShopee.DTO.User.PromotionInProductSearch(p.promotion.PromotionName,p.promotion.signature,p.promotion.promotionType,p.product.id) from PromotionItem p where p.product in :products")
        List<PromotionInProductSearch> getPromotionInfoInListProduct(List<Product> products);

        @Query("SELECT new com.CloneShopee.DTO.User.PromotionInProductSearch(p.promotion.PromotionName,p.promotion.signature,p.promotion.promotionType,p.product.id) from PromotionItem p where p.product.id = :productId")
        List<PromotionInProductSearch> getPromotionInfoByProductid(Integer productId);

        @Query("SELECT COUNT(p.id) from Promotion p JOIN PromotionItem pi on p.id=pi.promotion.id WHERE pi.product.id IN:productIds AND p.promotionType=:promotionType AND NOT (:endDate<=p.startDate OR :startDate>=p.endDate)")

        Integer countProductIsApplyPromotion(@Param("productIds") Set<Integer> productId,
                        @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        @Param("promotionType") String promotionType);

        @Modifying
        @Query("Delete Promotion p where p.id=:id and now()< p.startDate AND p.shop.id=:shopId")
        Integer deletePromotion(@Param("id") Integer promotionId, @Param("shopId") Integer shopId);

        @Query("SELECT COUNT(p.id) FROM PromotionComboOption p WHERE p IN :promotionOptions AND p.promotionCombo.id=:id")
        Integer countPromotionOptionsOfPromotion(@Param("promotionOptions") List<PromotionComboOption> promotions,
                        @Param("id") Integer id);

        @Query("SELECT p.product.id FROM PromotionItem p where p.promotion.id=:id")
        Set<Integer> getSetProductIdInPromotionItem(@Param("id") Integer id);

}

// SELECT COUNT(p.id) from Promotion p JOIN PromotionItem pi on
// p.id=pi.promotion.id WHERE pi.product.id IN:productIds
// AND p.promotionType=:promotionType AND (:startDate<p.startDate and
// :endDate>p.startDate or :startDate)