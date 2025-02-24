package com.CloneShopee.services.User;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CloneShopee.DTO.User.CartInfoDTO;
import com.CloneShopee.models.CartItem;
import com.CloneShopee.repository.CartItemRepository;
import com.CloneShopee.repository.ProductRepository;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private ProductRepository productRepository;

    public Boolean checkCartItemOfAccountId(Integer productId, Integer accountId) {
        return cartItemRepo.checkCartItemOfAccountId(productId, accountId).orElse(-1) != -1;
    }

    public void updateVariantCart(Integer productId1, Integer productIdChange, Integer accountId) {
        cartItemRepo.updateChangeVariantCart(productId1, accountId, productIdChange);
    }

    public List<CartInfoDTO> getAllCart(Integer accountId) {
        return cartItemRepo.getAllCartByAccountId(accountId);
    }

    public void addTocart(Integer productId, Integer quantity, Integer accountId) {
        CartItem cart = new CartItem(accountId, productId, quantity);
        cartItemRepo.saveAndFlush(cart);
    }

    public Boolean checkProductIsActiveAndQuantityIsOrder(Integer productId, Integer quantity) {
        Integer quantityProduct = productRepository.getQuantityProductVariantIfActive(productId).orElse(-1);
        return quantity <= quantityProduct && quantity != -1;
    }

    public Boolean deleteCart(Integer productId, Integer accountId) {
        return cartItemRepo.deleteCart(productId, accountId) == 1;
    }
}
