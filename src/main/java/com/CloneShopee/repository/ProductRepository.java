package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p.id from Product p where p.id =:productId and p.shop.id=:shopId")
    Integer getIdProductByProductIdAndShopId(@Param("productId") Integer productId, @Param("shopId") Integer shopId);

    @Query("SELECT p FROM Product p JOIN FETCH p.productVariants")
    List<Product> findAllProducts(Specification specs);

    @Modifying
    @Query("UPDATE Product p set p.status=:status WHERE p.id =:productId")
    Integer updateStatusByProductId(@Param("productId") Integer id, @Param("status") Integer status);

}
