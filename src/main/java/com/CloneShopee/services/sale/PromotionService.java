package com.CloneShopee.services.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.Promotion;
import com.CloneShopee.models.PromotionCombo;
import com.CloneShopee.models.PromotionComboOption;
import com.CloneShopee.models.PromotionItem;
import com.CloneShopee.models.PromotionProduct;
import com.CloneShopee.repository.ProductRepository;
import com.CloneShopee.repository.PromotionComboOptionRepository;
import com.CloneShopee.repository.PromotionComboRepository;
import com.CloneShopee.repository.PromotionItemRepository;
import com.CloneShopee.repository.PromotionProductRepository;
import com.CloneShopee.repository.PromotionRepository;

import jakarta.transaction.Transactional;

@Service
public class PromotionService {

    public static final String promotion_combo_type = "COMBO";
    public static final String promotion_product_type = "PRODUCT";

    @Autowired
    PromotionRepository promotionRepo;

    @Autowired
    PromotionComboOptionRepository promotionOptionRepo;

    @Autowired
    PromotionItemRepository promotionItemRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    PromotionComboRepository promotionComboRepo;

    @Autowired
    PromotionProductRepository promotionProductRepo;

    public List<Promotion> getAllPromotions() {
        return promotionRepo.findAll();
    }

    public void checkPromotionCombo(PromotionCombo promotionCombo) {
        switch (promotionCombo.getTypeCombo()) {
            case "PERSENT":
                checkConstraintValue(promotionCombo.getPromitionComboOptions(), 0.0, 100.0);
                break;
            case "PRICE":
                checkConstraintValue(promotionCombo.getPromitionComboOptions(), 1000.0, 1000000.0);
                break;
            case "DEAL":
                checkConstraintValue(promotionCombo.getPromitionComboOptions(), 1000.0, 1000000.0);
                break;
            default:
                throw new ConstraintException("typeCombo", "Not find type combo in your request !!!");
        }
        checkPromotionTime(promotionCombo);
    }

    public void checkPromotionTime(Promotion promotion) {
        if (promotion.getEndDate().before(promotion.getStartDate()) || promotion.getStartDate().before(new Date())) {
            throw new ConstraintException("startDate", "Start date just before end date and after now");
        }
    }

    public void checkConstraintValue(List<PromotionComboOption> options, Double minValue, Double maxValue) {
        options.forEach(v -> {
            if (v.getValueDiscountCustome() < minValue || v.getValueDiscountCustome() > maxValue) {
                throw new ConstraintException("promitionComboOptions", "Value discount not valid with type Combo !!!");
            }
        });
    }

    public void checkSortInPromtionOptions(List<PromotionComboOption> options) {
        if (options.size() > 1) {
            Integer i;
            for (i = 1; i < options.size(); i++) {
                if (options.get(i).getQuantityRequire() <= options.get(i - 1).getQuantityRequire()) {
                    throw new ConstraintException("promtionComboOptions",
                            "The order of quantity values must be ascending");
                }
            }
        }
    }

    public void checkProductIsApplingPromotion(Promotion promotion, Set<Integer> products) {
        if (promotionRepo.countProductIsApplyPromotion(products, promotion.getStartDate(), promotion.getEndDate(),
                promotion.getPromotionType()) > 0) {
            throw new ConstraintException("products", "List product is Appling promotion another!!!");
        }
    }

    public void savePromotionCombo(PromotionCombo promotion, Set<Integer> products) {
        List<PromotionItem> promotionItems = new ArrayList<>();
        promotionComboRepo.save(promotion);
        promotion.getPromitionComboOptions().forEach(v -> {
            v.setPromotionCombo(promotion);
        });
        products.forEach(v -> {
            promotionItems.add(new PromotionItem(promotion, new Product(v)));
        });
        promotionItemRepo.saveAll(promotionItems);
        promotionOptionRepo.saveAll(promotion.getPromitionComboOptions());
    }

    public void savePromotionProduct(PromotionProduct promotion, Set<Integer> products) {
        List<PromotionItem> promotionItems = new ArrayList<>();
        promotionProductRepo.save(promotion);
        products.forEach(v -> {
            promotionItems.add(new PromotionItem(promotion, new Product(v)));
        });
        promotionItemRepo.saveAll(promotionItems);
    }

    public void checkProductOfShop(Set<Integer> products, Integer shopId) {
        if (productRepo.countProductInListAndOfShop(products, shopId) != products.size()) {
            throw new ConstraintException("products", "Product is not valid");
        }
    }

    public boolean deletePromotion(Integer shopId, Integer promotionId) {
        return promotionRepo.deletePromotion(promotionId, shopId) == 1;
    }

    public void updatePromotionComboOptions(PromotionCombo promotion) {
        List<PromotionComboOption> promotions = promotion.getPromitionComboOptions().stream()
                .filter(option -> option.getId() != null).toList();

        if (promotionRepo.countPromotionOptionsOfPromotion(promotions, promotion.getId()) == promotions.size()) {
            checkSortInPromtionOptions(promotions);
            promotion.getPromitionComboOptions().forEach(v -> {
                v.setPromotionCombo(promotion);
            });
            promotionOptionRepo.deletePromotionOptionNotInlist(promotions, promotion.getId());
            promotionOptionRepo.saveAll(promotion.getPromitionComboOptions());
        } else {
            throw new ConstraintException("promotionOptions", "Promotion option is not valid!!!");
        }
    }

    public void updateItems(Promotion promotion, Set<Integer> promotionItems, Integer shopId) {
        checkProductOfShop(promotionItems, shopId);
        Set<Integer> promotionItemNow = promotionRepo.getSetProductIdInPromotionItem(promotion.getId());
        Set<Integer> promotionItemRemove = new HashSet<>(promotionItemNow);
        promotionItemRemove.removeAll(promotionItemNow);
        if (promotionItemRemove.size() > 0) {
            promotionItemRepo.deleteAllByIdInBatch(promotionItemRemove);
        }
        promotionItems.removeAll(promotionItemNow);
        if (promotionItems.size() > 0) {
            promotion.setPromotionItems(new ArrayList<>());
            promotionItems.forEach(v -> {
                promotion.getPromotionItems().add(new PromotionItem(promotion, new Product(v)));
            });
            promotionItemRepo.saveAll(promotion.getPromotionItems());
        }
    }

    public void updatePromotion(Promotion promotion) {
        promotionRepo.save(promotion);
    }

}
