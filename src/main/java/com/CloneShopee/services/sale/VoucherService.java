package com.CloneShopee.services.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.DTO.Sale.Voucher.VoucherInsert;
import com.CloneShopee.DTO.Sale.Voucher.VoucherUpdate;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.Shop;
import com.CloneShopee.models.VoucherShop;
import com.CloneShopee.models.VoucherShopItem;
import com.CloneShopee.repository.ProductRepository;
import com.CloneShopee.repository.VoucherRepository;
import com.CloneShopee.repository.VoucherShopItemRepository;

@Service
public class VoucherService {

    private final String voucher_type_persent = "PERSENT";
    private final String voucher_type_price = "PRICE";

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    VoucherRepository voucherRepo;
    @Autowired
    VoucherShopItemRepository voucherShopItemRepo;

    public VoucherShop getVoucherByIdAndStarting(Integer voucherId) {
        return voucherRepo.getVoucherByIdAndStarting(voucherId);
    }

    public Object getAllVoucher() {
        return voucherRepo.findAll();
    }

    public List<VoucherShop> getVoucherOfShopAndAccountReciveAndStarting(Integer accountId, Integer shopId) {
        return voucherRepo.getVoucherOfShopAndAccountReciveAndStarting(shopId, accountId);
    }

    public void checkVoucherOfShop(Integer id, Integer shop) {
        if (voucherRepo.getVoucherByIdAndShopId(id, shop).orElse(-1) == -1) {
            throw new ConstraintException("id", "Voucher update không hợp lệ !!!");
        }
    }

    public void saveVoucher(VoucherShop voucher, Shop shop, Set<Integer> productIds) {
        voucher.setShop(shop);
        voucher.setId(null);
        voucherRepo.save(voucher);
        List<VoucherShopItem> voucherItems = new ArrayList<>();
        for (Integer productId : productIds) {
            voucherItems.add(new VoucherShopItem(voucher, new Product(productId)));
        }
        voucherShopItemRepo.saveAll(voucherItems);
    }

    public void UpdateStatusVoucher(Integer voucherId, Integer isActive) {
        voucherRepo.updateStatusVoucherByVoucherId(voucherId, isActive);
    }

    public void checkvalueDisCount(String voucherType, Double disCountValue) {
        switch (voucherType) {
            case voucher_type_persent:
                if (disCountValue > 100) {
                    throw new ConstraintException("discountValue", "Mus lessthan 100");
                }
                break;
            case voucher_type_price:
                break;
            default:
                throw new ConstraintException("voucherType", "Not find voucher type");
        }
    }

    public void checkTimeVoucher(Date startDate, Date endDate) {
        if (startDate.before(new Date())) {
            throw new ConstraintException("startDate", "Start date must greaterthan now");
        }
        if (startDate.after(endDate)) {
            throw new ConstraintException("endDate", "EndDate must greaterThan startdate");
        }
    }

    public void checkProductOfShop(Set<Integer> products, Integer shopId) {
        if (productRepo.countProductInListAndOfShop(products, shopId) != products.size()) {
            throw new ConstraintException("products", "Product is not valid");
        }
    }

    //
    // public void handleItemVoucher(Integer voucherId, Set<Integer> productIds,
    // Boolean isChange) {
    // if (isChange) {
    // voucherRepo.deleteVoucherItemInProductIds(productIds);
    // Set<Integer> productIdsOld = voucherRepo.getProductIdsOfVoucher(productIds,
    // voucherId);
    // productIds.removeAll(productIdsOld);
    // List<VoucherShopItem> voucherShopItems = new ArrayList<>();
    // VoucherShop voucher = new VoucherShop(voucherId);
    // for (Integer productId : productIds) {
    // voucherShopItems.add(new VoucherShopItem(voucher, new Product(productId)));
    // }
    // voucherShopItemRepo.saveAll(voucherShopItems);
    // }
    // }

    // public void updateInforVoucher(VoucherUpdate voucher) {
    // voucherRepo.updateVoucherShop(voucher.getId(), voucher.getVoucherName(),
    // voucher.getStartDate(),
    // voucher.getEndDate(), voucher.getVoucherType(), voucher.getDiscountValue(),
    // voucher.getLimitUsage(),
    // voucher.getLimitValue(), voucher.getIsActive());
    // }

}
