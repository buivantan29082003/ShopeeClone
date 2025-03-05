package com.CloneShopee.services.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.User.OrderItemDTO;
import com.CloneShopee.DTO.User.ShopItemDTO;
import com.CloneShopee.models.Account;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.OrderItem;
import com.CloneShopee.models.ProductVariant;
import com.CloneShopee.models.Promotion;
import com.CloneShopee.models.Shop;
import com.CloneShopee.models.Status;
import com.CloneShopee.models.VoucherShop;
import com.CloneShopee.repository.CartItemRepository;
import com.CloneShopee.repository.OrderItemRepository;
import com.CloneShopee.repository.OrderRepository;
import com.CloneShopee.repository.PromotionItemRepository;
import com.CloneShopee.repository.VoucherRepository;

@Service
public class UserOrderService {
    @Autowired
    CartItemRepository cartItemRepo;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    ShopBean shopBean;

    @Autowired
    PromotionItemRepository pro;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    OrderItemRepository orderItemRepo;

    public List<OrderItemDTO> generationOrderInListIds(List<Integer> ids, Integer accountId) {
        return cartItemRepo.getInfoCartForAddOrder(ids, accountId);
    }

    public List<PromotionProjection> getPromotionInListProductIds(List<Integer> ids) {
        return pro.findProductPromotions(ids);
    }

    public void proccessOrder(ShopItemDTO shopItem, List<OrderItemDTO> items, Map<Integer, List<Promotion>> promotions,
            List<Order> orders, Status status, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setStatus(status);
        order.setOrderItems(new ArrayList<>());
        OrderItem orderItem;
        List<Promotion> l;
        Double ammountNotDiscount = 0.0;
        if (shopItem.getVoucherId() != null) {
            for (OrderItemDTO v : items) {
                orderItem = new OrderItem(new ProductVariant(v.getProductVariantId()), order, 1, v.getPrice());
                l = promotions.get(v.getProductId());
                if (l != null) {
                    for (Promotion vv : l) {
                        orderItem.setPrice(vv.caculatePrice(v.getQuantity(), orderItem.getPrice()));
                    }
                }
                ammountNotDiscount += v.getPrice();
                order.plusTotal(orderItem.caculatePrice());
                orderItems.add(orderItem);
            }
        } else {
            for (OrderItemDTO v : items) {
                orderItem = new OrderItem(new ProductVariant(v.getProductVariantId()), order, 1, v.getPrice());
                l = promotions.get(v.getProductId());
                if (l != null) {
                    for (Promotion vv : l) {
                        orderItem.setPrice(vv.caculatePrice(v.getQuantity(), orderItem.getPrice()));
                    }
                }
                order.plusTotal(orderItem.caculatePrice());
                orderItems.add(orderItem);
            }
        }

        order.setShop(new Shop(shopItem.getShopId()));
        order.setAccount(shopBean.getAccount());
        orders.add(order);
        checkVoucher(order, shopItem.getVoucherId(), shopItem.getShopId(), shopBean.getAccount().getId(),
                ammountNotDiscount);
    }

    public void checkVoucher(Order order, Integer voucherId, Integer shopId, Integer accountId,
            Double ammountNotDiscount) {
        if (voucherId != null) {
            VoucherShop voucherShop = voucherRepository.getVoucherShopIsApply(voucherId, shopId, accountId)
                    .orElse(null);
            if (voucherShop != null) {
                voucherShop.canculateOrder(order, ammountNotDiscount);
                updateOrderQuantityUsed(voucherId, accountId);
                order.setVoucherShop(voucherShop);
            } else {
                throw new RuntimeException(" voucher shop này ko áp dụng được ");
            }
        }
    }

    public void saveOrder(List<Order> orders, List<OrderItem> orderItems) {
        orderRepo.saveAll(orders);
        orderItemRepo.saveAll(orderItems);
    }

    public void updateOrderQuantityUsed(Integer voucherId, Integer accountId) {
        voucherRepository.updateQuantityUsed(voucherId, accountId);
    }

    public Boolean generateTokenShip(List<ShopItemDTO> shops) {
        for (ShopItemDTO v : shops) {
            v.getTokenShip().checkGenerate(null);
        }
        return true;
    }

}
