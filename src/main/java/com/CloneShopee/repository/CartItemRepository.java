package com.CloneShopee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.DTO.User.CartInfoDTO;
import com.CloneShopee.models.CartDetailId;
import com.CloneShopee.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, CartDetailId> {

    @Query("SELECT p.product.id FROM CartItem p where p.product.id=:productId and p.account.id=:accountId")
    public Optional<Integer> checkCartItemOfAccountId(@Param("productId") Integer productId,
            @Param("accountId") Integer accountId);

    @Query("SELECT p.quantity as quantity,p.product.product.id as productId,p.product.product.productName as productName , p.product.id as variantId, p.product.variantName as variantName,p.product.product.shop.id as shopId, p.product.product.shop.shopName as shopName from CartItem p where p.account.id=:accountId")
    public List<CartInfoDTO> getAllCartByAccountId(@Param("accountId") Integer accountId);

    @Modifying
    @Query("UPDATE CartItem p SET p.product.id=:idChange where p.product.id=:productId and p.account.id=:accountId ")
    void updateChangeVariantCart(@Param("productId") Integer productId, @Param("accountId") Integer accountId,
            @Param("idChange") Integer idChange);

}
