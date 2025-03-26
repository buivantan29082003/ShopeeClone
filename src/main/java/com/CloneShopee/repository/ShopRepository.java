package com.CloneShopee.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CloneShopee.models.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Query("select p from Shop p where p.id in:shopIds")
    List<Shop> getShopInList(@Param("shopIds") Set<Integer> shopIds);
}
