package com.CloneShopee.controllers.sale;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.Sale.Promotion.PromotionInsert;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.Promotion;
import com.CloneShopee.models.PromotionCombo;
import com.CloneShopee.models.PromotionComboOption;
import com.CloneShopee.models.PromotionProduct;
import com.CloneShopee.services.sale.PromotionService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class PromotionController {

        @Autowired
        PromotionService promotionService;

        @Autowired
        ShopBean shopbean;

        @GetMapping("sale/promotion/findall")
        public ResponseEntity<Object> getAllPromotions() {
                return new ResponseEntity(promotionService.getAllPromotions(), HttpStatus.OK);
        }

        @Transactional
        @PutMapping("sale/promotion/delete")
        public ResponseEntity<Object> deletePromotion(@RequestParam("promotionId") Integer id) {
                return new ResponseEntity<>(promotionService.deletePromotion(shopbean.getShop().getId(), id)
                                ? "Delete successfully"
                                : "Khuyến mãi ko hợp lệ, chương trình có thể đã diễn ra hoặc ko phải của shop",
                                HttpStatus.OK);
        }

        @Transactional
        @PostMapping("sale/promotion/add/combo")
        public ResponseEntity<Object> addPromotionCombo(@RequestBody PromotionInsert<PromotionCombo> promotion) {
                promotion.getPromotion().setPromotionType(PromotionService.promotion_combo_type);
                promotion.getPromotion().setShop(shopbean.getShop());
                promotionService.checkPromotionCombo(promotion.getPromotion());
                promotionService.checkSortInPromtionOptions(promotion.getPromotion().getPromitionComboOptions());
                promotionService.checkProductOfShop(promotion.getProductIds(),
                                shopbean.getShop().getId());
                promotionService.checkProductIsApplingPromotion(promotion.getPromotion(),
                                promotion.getProductIds());
                promotionService.savePromotionCombo(promotion.getPromotion(),
                                promotion.getProductIds());
                return new ResponseEntity(promotion.getPromotion().getId(), HttpStatus.OK);
        }

        @Transactional
        @PostMapping("sale/promotion/add/product")
        public ResponseEntity<Object> addPromotionProduct(
                        @RequestBody PromotionInsert<PromotionProduct> promotion) {
                promotion.getPromotion().setPromotionType(PromotionService.promotion_product_type);
                promotion.getPromotion().setShop(shopbean.getShop());
                promotionService.checkProductOfShop(promotion.getProductIds(),
                                shopbean.getShop().getId());
                promotionService.checkProductIsApplingPromotion(promotion.getPromotion(),
                                promotion.getProductIds());

                promotionService.savePromotionProduct(promotion.getPromotion(), promotion.getProductIds());
                return new ResponseEntity(promotion.getPromotion().getId(), HttpStatus.OK);
        }

        @Transactional
        @PutMapping("sale/promotion/update/combo")
        public ResponseEntity<Object> updatePromotionCombo(@RequestBody PromotionInsert<PromotionCombo> promotion) {
                promotion.getPromotion().setShop(shopbean.getShop());
                promotionService.updatePromotionComboOptions(promotion.getPromotion());
                promotionService.updateItems(promotion.getPromotion(), promotion.getProductIds(),
                                shopbean.getShop().getId());
                promotionService.updatePromotion(promotion.getPromotion());
                return new ResponseEntity<>(null, HttpStatus.OK);
        }

        @Transactional
        @PutMapping("sale/promotion/update/product")
        public ResponseEntity<Object> updatePromotionProduct(@RequestBody PromotionInsert<PromotionCombo> promotion) {
                promotion.getPromotion().setShop(shopbean.getShop());
                promotionService.updateItems(promotion.getPromotion(), promotion.getProductIds(),
                                shopbean.getShop().getId());
                promotionService.updatePromotion(promotion.getPromotion());
                return new ResponseEntity<>(null, HttpStatus.OK);
        }

        @PostMapping("/sale/promotion/change-status")
        public ResponseEntity<BaseRespone> changeStatusPromotion(
                        @RequestParam(name = "promotionId", defaultValue = "-1") Integer promotionId,
                        @RequestParam(name = "statusNumber", defaultValue = "1") Integer statusNumber) {
                if (statusNumber != 0 || statusNumber != 1) {
                        return new ResponseEntity(new BaseRespone(null, "Trạng thái cập nhật không hợp lệ !!!"),
                                        HttpStatus.BAD_REQUEST);
                }
                Integer promotion = promotionService.getPromotionByIdAndShopId(promotionId, shopbean.getShop().getId());
                if (promotion != null) {
                        Integer isUpdated = promotionService.changeStatusPromotion(promotionId, 1);
                        if (isUpdated == 1) {
                                return new ResponseEntity(new BaseRespone(null, "Thay đổi trạng thái thành công!!!"),
                                                HttpStatus.OK);
                        }
                        return new ResponseEntity(new BaseRespone(null, "Nó bị  !!!"), HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity(new BaseRespone(null, "Không tìm thấy promotion !!!"),
                                HttpStatus.BAD_REQUEST);
        }

}
