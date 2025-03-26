package com.CloneShopee.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.DTO.Sale.OrderInfo;
import com.CloneShopee.DTO.Sale.OrderItemDTO;
import com.CloneShopee.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

       @Query("""
                     SELECT p.id AS id,
                            p.status AS status,
                            p.payment AS payment,
                            p.createdDate AS createdDate,
                            p.totalAmount AS totalAmount,
                            p.fullAddress AS fullAddress,
                            p.account.id AS accountId,
                            p.account.id AS accountFullName
                     FROM Order p
                     WHERE p.shop.id = :shopId
                     """)
       Page<OrderInfo> getOrderInfos(
                     @Param("shopId") Integer shopId,
                     @Param("statusId") Integer statusId,
                     @Param("paymentId") Integer paymentId,
                     Pageable page);

       @Query("""
                         SELECT p.id as id,
                                st as status,
                                pm as payment,
                                p.createdDate as createdDate,
                                p.totalAmount as totalAmount,
                                p.fullAddress as fullAddress,
                                p.account.id as accountId,
                                p.account.fullName as accountFullName
                         FROM Status st cross join  Payment pm  join   Order p on p.status=st and p.payment=pm
                     """)
       List<OrderInfo> getOrderInfosa();

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

       @Query("select count(p.id) from Order p where p.shop.id=:shopId and p.account.id=:accountId")
       public Optional<Integer> checkNewCustomer(@Param("shopId") Integer shopId,
                     @Param("accountId") Integer accountId);

       @Query("SELECT COUNT(p.id) FROM Order p WHERE p.account.id = :accountId AND p.createdDate >=:dateNear")
       public Integer checkBuyBack(@Param("dateNear") Date dateNear,
                     @Param("accountId") Integer accountId);

}
