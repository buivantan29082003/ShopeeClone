package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Property;
import com.CloneShopee.models.PropertyItem;

public interface PropertyItemsRepository extends JpaRepository<PropertyItem,Integer>{

	// SELECT
	@Query("SELECT p from PropertyItem p WHERE p.product.id=:productId")
	public List<PropertyItem> getPropertyItemInList(@Param("productId") Integer id);
	
	
	
	// UPDATE
	
	@Modifying
	@Query("DELETE PropertyItem p WHERE p.product.id=:productId")
	public void deleteAllPropertyByProductId(@Param("productId") Integer id);
}
