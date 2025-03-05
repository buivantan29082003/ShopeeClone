package com.CloneShopee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.DTO.User.CartInfoDTO;
import com.CloneShopee.DTO.User.OrderItemDTO;
import com.CloneShopee.models.CartDetailId;
import com.CloneShopee.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, CartDetailId> {

        @Query("SELECT p.product.id FROM CartItem p WHERE p.product.id=:productId AND p.account.id=:accountId")
        public Optional<Integer> checkCartItemOfAccountId(@Param("productId") Integer productId,
                        @Param("accountId") Integer accountId);

        @Query("SELECT p.quantity as quantity,p.product.product.id as productId,p.product.product.productName as productName , p.product.id as variantId, p.product.variantName as variantName,p.product.product.shop.id as shopId, p.product.product.shop.shopName as shopName from CartItem p where p.account.id=:accountId")
        public List<CartInfoDTO> getAllCartByAccountId(@Param("accountId") Integer accountId);

        @Modifying
        @Query("UPDATE CartItem p SET p.product.id=:idChange WHERE p.product.id=:productId AND p.account.id=:accountId ")
        void updateChangeVariantCart(@Param("productId") Integer productId, @Param("accountId") Integer accountId,
                        @Param("idChange") Integer idChange);

        @Modifying
        @Query("DELETE CartItem p WHERE p.product.id=:productId AND p.account.id=:accountId ")
        public Integer deleteCart(@Param("productId") Integer productId, @Param("accountId") Integer accountId);

        @Query("SELECT p.product.id as productVariantId,p.product.product.id as productId,p.product.product.shop.id as shopId,p.quantity as quantity,p.product.price as price FROM CartItem p  WHERE p.product.id IN:ids AND p.account.id=:accountId AND p.product.isActive=1 AND p.product.product.status=1 ")
        public List<OrderItemDTO> getInfoCartForAddOrder(@Param("ids") List<Integer> ids,
                        @Param("accountId") Integer accountId);

}
