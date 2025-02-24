package com.CloneShopee.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query("SELECT count(p.product.id) FROM ProductVariant p where (p.id=:productId1 or p.id=:productId2) and p.product.id=:productId and p.isActive=1")
    Integer checkVariantSameProduct(@Param("productId1") Integer productId1,
            @Param("productId2") Integer productId2, @Param("productId") Integer productid);

    @Query("SELECT p.id from Product p where p.id =:productId and p.shop.id=:shopId")
    Integer getIdProductByProductIdAndShopId(@Param("productId") Integer productId, @Param("shopId") Integer shopId);

    @Query("SELECT p.quantity from ProductVariant p where p.id =:productId and p.isActive=1 and p.product.status=1")
    Optional<Integer> getQuantityProductVariantIfActive(@Param("productId") Integer productId);

    @Query("SELECT COUNT(p.id) FROM Product p WHERE  p.id IN :products AND p.shop.id=:shopId")
    Integer countProductInListAndOfShop(@Param("products") Set<Integer> products, @Param("shopId") Integer shopId);

    @EntityGraph(attributePaths = { "promotionItems", "productVariants" })
    @Query("SELECT p FROM Product p")
    List<Product> findProductWithDetails();

    @Modifying
    @Query("UPDATE Product p set p.status=:status WHERE p.id =:productId")
    Integer updateStatusByProductId(@Param("productId") Integer id, @Param("status") Integer status);

}
