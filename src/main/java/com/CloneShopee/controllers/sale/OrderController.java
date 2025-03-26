package com.CloneShopee.controllers.sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.Sale.OrderInfo;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.ResponeEntity.PageFormat;
import com.CloneShopee.models.Order;
import com.CloneShopee.services.sale.OrderService;
import com.CloneShopee.services.sale.StatusCustome;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    ShopBean shopBean;

    @GetMapping("sale/order/findall")
    public ResponseEntity<Object> getAllOrder(@RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "statusId", defaultValue = "-1") Integer statusId,
            @RequestParam(name = "paymentId", defaultValue = "-1") Integer paymentId) {
        Pageable pageSearch = PageRequest.of(page, pageSize);
        Page<Order> data = orderService.getAllOrder(shopBean.getShop().getId(), statusId, paymentId, pageSearch);
        List<OrderInfo> orders = new ArrayList<OrderInfo>();
        data.getContent().forEach(v -> {
            orders.add(new OrderInfo(v));
        });
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("sale/order/finditems")
    public ResponseEntity<Object> getAllOrder(@RequestParam(name = "orderId", defaultValue = "-1") Integer orderId) {
        return new ResponseEntity<>(orderService.getOrderItemByOrderId(orderId, shopBean.getShop().getId()),
                HttpStatus.OK);
    }

    @Transactional
    @PutMapping("sale/order/update/status")
    public ResponseEntity<Object> updateStatusOrders(
            @RequestParam(name = "status", defaultValue = "") String statusName,
            @RequestBody Set<Integer> orderIds) {
        StatusCustome status = OrderService.statusCode.get(statusName);
        if (status != null && status.getStatusId() > 1 && status.getStatusId() < 4) {
            orderService.checkStatusOrdersAndOfShop(orderIds, shopBean.getShop().getId(), status.getStatusId() - 1);
            orderService.updateStatusAndTagOrder(orderIds, status.getStatusId(), "");
            return new ResponseEntity<>("Update status order successfully", HttpStatus.OK);
        }
        throw new ConstraintException("status", "Status code is not valid");
    }

    @PutMapping("sale/order/update/status/single")
    @Transactional
    public ResponseEntity<Object> updateStatusOrder(@RequestParam(name = "status", defaultValue = "") String statusName,
            @RequestBody Integer orderId) {
        StatusCustome status = OrderService.statusCode.get(statusName);
        if (status != null && status.getStatusId() > 1 && status.getStatusId() < 4) {
            if (orderService.checkOrderOfShop(orderId, shopBean.getShop().getId(), status.getStatusId())) {
                orderService.updateStatusAndTagOrder(orderId, status.getStatusId(), "");
                return new ResponseEntity<>("Update status order successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Order with status is not valid", HttpStatus.OK);
        }
        return new ResponseEntity<>("Status code is not valid", HttpStatus.OK);
    }

    public ResponseEntity<Object> cancelOrder(@RequestParam(name = "orderId", defaultValue = "-1") Integer orderId) {
        Boolean isOfShop = orderService.checkOrderOfShop(orderId, shopBean.getShop().getId());
        if (isOfShop) {

        }
        return new ResponseEntity<>(new BaseRespone(null, "Không tìm thấy order trong shop"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("getProduct")
    public ResponseEntity<Object> addProductForLive() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
