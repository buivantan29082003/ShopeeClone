package com.CloneShopee.repository.SpecificationBuilder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.CloneShopee.models.Order;

import jakarta.persistence.criteria.Predicate;

public class OrderIrtemSpecify {
    public static Specification<Order> hasStatus(Integer statusId) {
        return (root, query, criteriaBuilder) -> {
            if (statusId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status").get("id"), statusId);
        };
    }

    public static Specification<Order> filterBy(Integer shopId, Integer statusId, Integer paymentId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Điều kiện bắt buộc: shopId
            predicates.add(criteriaBuilder.equal(root.get("shop").get("id"), shopId));

            // Nếu statusId khác -1 thì thêm điều kiện lọc theo statusId
            if (statusId != -1) {
                predicates.add(criteriaBuilder.equal(root.get("status").get("id"), statusId));
            }

            if (paymentId != -1) {
                predicates.add(criteriaBuilder.equal(root.get("payment").get("id"), paymentId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}