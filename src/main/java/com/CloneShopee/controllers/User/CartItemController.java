package com.CloneShopee.controllers.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.User.CartInfoDTO;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.services.User.CartItemService;
import com.CloneShopee.services.User.ProductServiceUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    ProductServiceUser productService;

    @Autowired
    ShopBean shopBean;

    @Transactional
    @PostMapping("user/cart/addtocart")
    public ResponseEntity<Object> addToCart(@RequestParam(name = "productId", defaultValue = "-1") Integer productId,
            @RequestParam(name = "quantity", defaultValue = "1") Integer quantity) {
        if (quantity > 0 && cartItemService.checkProductIsActiveAndQuantityIsOrder(productId, quantity)) {
            cartItemService.addTocart(productId, quantity, shopBean.getAccountId());
            return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Data request is not valid"), HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @DeleteMapping("user/cart/delete")
    public ResponseEntity<Object> deleteCart(@RequestParam(name = "productId", defaultValue = "-1") Integer productId) {
        if (cartItemService.deleteCart(productId, shopBean.getAccountId())) {
            return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Cart item is not valid"), HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PutMapping("user/cart/changevariant")
    public ResponseEntity<Object> changeVariantInCartItem(
            @RequestParam(name = "productId", defaultValue = "-1") Integer productId,
            @RequestParam(name = "idChange", defaultValue = "-1") Integer idChange,
            @RequestParam(name = "idProduct", defaultValue = "-1") Integer idProduct) {
        if (cartItemService.checkCartItemOfAccountId(productId, shopBean.getAccountId())) {
            if (productService.isSameProduct(productId, idChange, idProduct)) {
                cartItemService.updateVariantCart(productId, shopBean.getAccountId(), idChange);
                return new ResponseEntity<>(new BaseRespone(null, "Success"), HttpStatus.OK);
            }
            return new ResponseEntity<>(
                    new BaseRespone(null,
                            "Product variant is not same product with cart item change or variant not saling"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Cart item is not valid"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("user/cart/findall")
    public ResponseEntity<Object> getAll() {
        List<CartInfoDTO> cartInfoDTOs = cartItemService.getAllCart(shopBean.getAccountId());
        Map<Integer, List<CartInfoDTO>> groupedByShop = cartInfoDTOs.stream()
                .collect(Collectors.groupingBy(CartInfoDTO::getShopId));
        List<Shop> s = new ArrayList<>();
        groupedByShop.forEach((v, k) -> {
            s.add(new Shop(v, k.get(0).getShopName(), k));
        });
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

}

class Cart {
    @JsonIgnore
    private Shop s;
    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public Shop getShop() {
        return s;
    }

    public Cart(Integer productId, Integer shopId) {
        this.productId = productId;
        this.s = new Shop(shopId);
    }

    @Override
    public String toString() {
        return "Cart{productId=" + productId + ", shop=" + s + "}";
    }
}

class Shop {
    private Integer shopId;
    private String shopName;
    List<CartInfoDTO> items;

    public Shop() {

    }

    public Shop(Integer shopId, String shopName, List<CartInfoDTO> items) {
        this.items = items;
        this.shopId = shopId;
        this.shopName = shopName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public Shop(Integer id) {
        this.shopId = id;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public boolean equals(Object o) {
        Shop shop = (Shop) o;
        return Objects.equals(shopId, shop.shopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopId);
    }

    @Override
    public String toString() {
        return "Shop{id=" + shopId + "}";
    }

    public List<CartInfoDTO> getItems() {
        return items;
    }

    public void setItems(List<CartInfoDTO> items) {
        this.items = items;
    }
}