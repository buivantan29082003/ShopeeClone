package com.CloneShopee.repository.SpecificationBuilder;

import org.springframework.data.jpa.domain.Specification;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.Payment;
import com.CloneShopee.models.Status;
import com.CloneShopee.models.Account; // ⚠ THÊM IMPORT ACCOUNT

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {
    public static Specification<Order> filterBy(Integer shopId, Integer statusId, Integer paymentId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (statusId != -1) {
                predicates.add(criteriaBuilder.equal(root.get("status").get("id"), statusId));
            }
            if (paymentId != -1) {
                predicates.add(criteriaBuilder.equal(root.get("payment").get("id"), paymentId));
            }
            if (shopId != null) {
                predicates.add(criteriaBuilder.equal(root.get("shop").get("id"), shopId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Order> filterByAccountId(Integer accountId, Integer statusId, Integer paymentId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (statusId != -1) {
                predicates.add(criteriaBuilder.equal(root.get("status").get("id"), statusId));
            }
            if (paymentId != -1) {
                predicates.add(criteriaBuilder.equal(root.get("payment").get("id"), paymentId));
            }
            predicates.add(criteriaBuilder.equal(root.get("account").get("id"), accountId));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
