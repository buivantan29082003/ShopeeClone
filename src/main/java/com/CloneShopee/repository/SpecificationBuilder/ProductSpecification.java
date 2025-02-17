package com.CloneShopee.repository.SpecificationBuilder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.CloneShopee.models.Product;
import jakarta.persistence.criteria.*;

public class ProductSpecification {

    public static Specification<Product> filterProductsWithJoinfetch(String name, List<Integer> categoryIds,
            Integer status) {
        return (root, query, criteriaBuilder) -> {
            // root.fetch("promotionItems", JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + name + "%"));
            }
            if (categoryIds != null && !categoryIds.isEmpty()) {
                predicates.add(root.get("category").get("id").in(categoryIds));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Product> filterProducts(String name, List<Integer> categoryIds, Integer status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + name + "%"));
            }
            if (categoryIds != null) {
                predicates.add(root.get("category").get("id").in(categoryIds));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
