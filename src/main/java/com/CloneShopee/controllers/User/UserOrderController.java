package com.CloneShopee.controllers.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.User.OrderInsertDTO;
import com.CloneShopee.DTO.User.OrderItemDTO;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.OrderItem;
import com.CloneShopee.models.Promotion;
import com.CloneShopee.models.Status;
import com.CloneShopee.services.User.PromotionProjection;
import com.CloneShopee.services.User.UserOrderService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class UserOrderController {

    @Autowired
    UserOrderService orderService;
    @Autowired
    ShopBean shopBean;

    @Transactional
    @PostMapping("user/order/add")
    public ResponseEntity<Object> addOrder(@RequestBody @Valid OrderInsertDTO order) {
        List<OrderItemDTO> orderItems = orderService.generationOrderInListIds(order.getCartItems(),
                shopBean.getAccountId());
        if (orderItems.size() == order.getCartItems().size()) {
            Map<Integer, List<OrderItemDTO>> mapShop = orderItems.stream().distinct()
                    .collect(Collectors.groupingBy(OrderItemDTO::getShopId));
            List<Integer> productIds = orderItems.stream().map(OrderItemDTO::getProductId).collect(Collectors.toList());
            List<PromotionProjection> promotions = orderService.getPromotionInListProductIds(productIds);
            Map<Integer, List<Promotion>> groupedPromotions = promotions.stream()
                    .collect(Collectors.groupingBy(
                            PromotionProjection::getProductId,
                            Collectors.mapping(PromotionProjection::getPromotion, Collectors.toList())));
            List<Order> orders = new ArrayList<>();
            Status status = new Status(1);
            List<OrderItem> orderItemss = new ArrayList<>();
            order.getShops().forEach(v -> {
                orderService.proccessOrder(v, mapShop.get(v.getShopId()), groupedPromotions, orders, status,
                        orderItemss);
            });
            orderService.saveOrder(orders, orderItemss);
            return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Cart item is not valid"), HttpStatus.BAD_REQUEST);
    }

}