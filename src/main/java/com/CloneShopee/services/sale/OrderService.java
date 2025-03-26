package com.CloneShopee.services.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.CloneShopee.DTO.Sale.OrderInfo;
import com.CloneShopee.DTO.Sale.OrderItemDTO;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.models.Order;
import com.CloneShopee.repository.OrderItemRepository;
import com.CloneShopee.repository.OrderRepository;
import com.CloneShopee.repository.SpecificationBuilder.OrderIrtemSpecify;
import com.CloneShopee.repository.SpecificationBuilder.OrderSpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class OrderService {

    public static final Map<String, StatusCustome> statusCode = Map.of("UNPAID", new StatusCustome(1, new HashMap<>()),
            "TO_SHIP", new StatusCustome(2, Map.of("TO_PROCCESS", "Đang xử lý", "PROCCESSED", "Đã xử lý")),
            "SHIPPING", new StatusCustome(3, new HashMap<>()),
            "COMPLETE", new StatusCustome(4, new HashMap<>()),
            "SHIPPING_FAIL",
            new StatusCustome(5, Map.of("RETURNING", "Đang trả hàng", "RETURNED", "Đã trả hàng", "LOST", "Thất lạc")),
            "CANCEL", new StatusCustome(6,
                    Map.of("TO_PROCCESS", "Đang xử lý", "RETURNED", "Đã hoàn tiền", "PROCCESSED", "Đã xử lý")));

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    OrderItemRepository orderItemRepo;

    public org.springframework.data.domain.Page<Order> getAllOrder(Integer shopId, Integer statusId,
            Integer paymentId,
            org.springframework.data.domain.Pageable page) {
        Specification<Order> spec = OrderSpecification.filterBy(shopId, statusId, paymentId);

        return orderRepo.findAll(spec, page);
    }

    public Boolean checkOrderOfShop(Integer orderId, Integer shopId) {
        return orderRepo.checkOrderOfShop(orderId, shopId).orElse(-1) != -1;
    }

    public Boolean checkOrderOfShop(Integer orderId, Integer shopId, Integer statusId) {
        return orderRepo.checkOrderOfShopAndStatusId(orderId, shopId, statusId).orElse(-1) != -1;
    }

    public List<OrderItemDTO> getOrderItemByOrderId(Integer orderId, Integer shopId) {
        if (checkOrderOfShop(orderId, shopId)) {
            return orderRepo.getOrderItemsByOrderId(orderId);
        }
        throw new ConstraintException("order", "Order not of shop");
    }

    public void checkStatusOrdersAndOfShop(Set<Integer> orderIds, Integer shopId, Integer statusId) {
        Integer countOrderIds = orderRepo.countOrderByStatusAndOfShop(orderIds, statusId, shopId);
        if (countOrderIds != orderIds.size()) {
            throw new ConstraintException("orders", "Order or status is Not valid");
        }
    }

    public void updateStatusAndTagOrder(Set<Integer> orderIds, Integer statusId, String tag) {
        orderRepo.updateStatusAndTagOrders(orderIds, tag, statusId);
    }

    public void updateStatusAndTagOrder(Integer orderId, Integer statusId, String tag) {
        orderRepo.updateStatusAndTagOrders(orderId, tag, statusId);
    }
}
