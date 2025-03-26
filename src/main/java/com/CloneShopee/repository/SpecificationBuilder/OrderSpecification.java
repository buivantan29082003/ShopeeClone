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

            // JOIN status, payment và account
            Join<Order, Status> statusJoin = root.join("status", JoinType.INNER);
            Join<Order, Payment> paymentJoin = root.join("payment", JoinType.INNER);
            Join<Order, Account> accountJoin = root.join("account", JoinType.INNER); // ⚠ THÊM JOIN ACCOUNT

            // Điều kiện bắt buộc: shopId
            if (shopId != null) {
                predicates.add(criteriaBuilder.equal(root.get("shop").get("id"), shopId));
            }

            // Điều kiện tùy chọn: statusId (nếu khác -1)
            if (statusId != -1) {
                predicates.add(criteriaBuilder.equal(statusJoin.get("id"), statusId));
            }

            // Điều kiện tùy chọn: paymentId (nếu khác -1)
            if (paymentId != -1) {
                predicates.add(criteriaBuilder.equal(paymentJoin.get("id"), paymentId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
