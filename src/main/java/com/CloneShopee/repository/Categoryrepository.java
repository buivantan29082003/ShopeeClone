package com.CloneShopee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Category;

public interface Categoryrepository extends JpaRepository<Category, Integer> {
	@Query("SELECT p.id from Category p where p.id = :id")
	public Optional<Integer> getBrandById(@Param("id") Integer id);

	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL")
	List<Category> findAllParentCategories();

}
