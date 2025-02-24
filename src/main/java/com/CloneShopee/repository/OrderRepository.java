package com.CloneShopee.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.DTO.Sale.OrderInfo;
import com.CloneShopee.DTO.Sale.OrderItemDTO;
import com.CloneShopee.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

        @Query("SELECT p.id as id,p.status as status, p.payment as payment,p.createdDate as createdDate,p.totalAmount as totalAmount,p.fullAddress as fullAddress,p.account.id as accountId,p.account.fullName  as accountFullName FROM Order p")
        List<OrderInfo> getOrderInfos();

        @Query("SELECT p.quantity as quantity, p.price as price, p.product.id as productVariantId,p.product.product.id as productId,p.product.image as productImage,p.order.id as orderId FROM OrderItem p WHERE p.order.id IN :orderIds")
        List<OrderItemDTO> getOrderItemsInOrderList(@Param("orderIds") List<Integer> orderIds);

        @Query("SELECT p.quantity as quantity, p.price as price,p.product.product.productName as productName,p.product.variantName as variantName, p.product.id as productVariantId,p.product.product.id as productId,p.product.image as productImage,p.order.id as orderId FROM OrderItem p")
        List<OrderItemDTO> getOrderItemsByOrderId(@Param("orderId") Integer orderId);

        @Query("SELECT p.id FROM Order p where p.id=:orderId and p.shop.id=:shopId")
        Optional<Integer> checkOrderOfShop(@Param("orderId") Integer orderId, @Param("shopId") Integer shopId);

        @Query("SELECT p.id FROM Order p where p.id=:orderId and p.shop.id=:shopId and p.status.id=:statusId")
        Optional<Integer> checkOrderOfShopAndStatusId(@Param("orderId") Integer orderId,
                        @Param("shopId") Integer shopId,
                        @Param("statusId") Integer statusId);

        @Query("SELECT COUNT(p.id) FROM Order p where p.id IN:orderIds and p.status.id=:statusId and p.shop.id=:shopId")
        Integer countOrderByStatusAndOfShop(@Param("orderIds") Set<Integer> orderIds,
                        @Param("statusId") Integer statusId,
                        @Param("shopId") Integer shopId);

        @Modifying
        @Query("UPDATE Order p SET p.status.id=:statusId , p.tag=:tag where p.id IN:orderIds")
        void updateStatusAndTagOrders(@Param("orderIds") Set<Integer> orderIds, @Param("tag") String tag,
                        @Param("statusId") Integer statusId);

        @Modifying
        @Query("UPDATE Order p SET p.status.id=:statusId , p.tag=:tag where p.id =:orderId")
        void updateStatusAndTagOrders(@Param("orderId") Integer orderIds, @Param("tag") String tag,
                        @Param("statusId") Integer statusId);

}
