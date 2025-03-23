package com.CloneShopee.controllers.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.Sale.Voucher.VoucherInsert;
import com.CloneShopee.DTO.Sale.Voucher.VoucherUpdate;
import com.CloneShopee.models.VoucherBuyBack;
import com.CloneShopee.models.VoucherFolower;
import com.CloneShopee.models.VoucherShop;
import com.CloneShopee.services.sale.VoucherService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private ShopBean shopbean;

    @Transactional
    @PostMapping("sale/voucher/add")
    public ResponseEntity<Object> addVoucher(@RequestBody @Valid VoucherInsert<VoucherShop> voucher) {
        voucherService.checkProductOfShop(voucher.getProductIds(), shopbean.getShop().getId());
        voucherService.checkTimeVoucher(voucher.getVoucher().getStartDate(), voucher.getVoucher().getEndDate());
        voucherService.checkvalueDisCount(voucher.getVoucher().getVoucherType(),
                voucher.getVoucher().getDiscountValue());
        voucherService.saveVoucher(voucher.getVoucher(), shopbean.getShop(), voucher.getProductIds());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("sale/voucher/add/folower")
    public ResponseEntity<Object> addVoucherFolower(@RequestBody @Valid VoucherInsert<VoucherFolower> voucher) {
        voucherService.checkProductOfShop(voucher.getProductIds(), shopbean.getShop().getId());
        voucherService.checkTimeVoucher(voucher.getVoucher().getStartDate(), voucher.getVoucher().getEndDate());
        voucherService.checkvalueDisCount(voucher.getVoucher().getVoucherType(),
                voucher.getVoucher().getDiscountValue());
        voucherService.saveVoucher(voucher.getVoucher(), shopbean.getShop(), voucher.getProductIds());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("sale/voucher/add/buyback")
    public ResponseEntity<Object> addVoucherBuyBack(@RequestBody @Valid VoucherInsert<VoucherBuyBack> voucher) {
        voucherService.checkProductOfShop(voucher.getProductIds(), shopbean.getShop().getId());
        voucherService.checkTimeVoucher(voucher.getVoucher().getStartDate(), voucher.getVoucher().getEndDate());
        voucherService.checkvalueDisCount(voucher.getVoucher().getVoucherType(),
                voucher.getVoucher().getDiscountValue());
        voucherService.saveVoucher(voucher.getVoucher(), shopbean.getShop(), voucher.getProductIds());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("sale/voucher/findall")
    public ResponseEntity<Object> getVoucher() {
        return new ResponseEntity<Object>(voucherService.getAllVoucher(), HttpStatus.OK);
    }

    @PutMapping("sale/voucher/update")
    public ResponseEntity<Object> updateVoucher(@RequestBody @Valid VoucherUpdate voucher) {
        voucherService.checkVoucherOfShop(voucher.getId(), shopbean.getShop().getId());
        voucherService.checkProductOfShop(voucher.getProductIds(), shopbean.getShop().getId());
        voucherService.checkTimeVoucher(voucher.getStartDate(), voucher.getEndDate());
        voucherService.checkvalueDisCount(voucher.getVoucherType(), voucher.getDiscountValue());
        voucherService.handleItemVoucher(voucher.getId(), voucher.getProductIds(), voucher.getIsChangeItems());
        voucherService.updateInforVoucher(voucher);
        return new ResponseEntity<>("Update successfully!!!", HttpStatus.OK);
    }

    public ResponseEntity<Object> updateStatusVoucher(
            @RequestParam(name = "voucherId", defaultValue = "-1") Integer voucherd,
            @RequestParam(name = "statusCode", defaultValue = "1") Integer statusCode) {
        voucherService.checkVoucherOfShop(voucherd, shopbean.getShop().getId());
        if (statusCode > -1 && statusCode < 2) {
            updateStatusVoucher(voucherd, statusCode);
            return new ResponseEntity<>("change status successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("StatusCode is not valid", HttpStatus.BAD_REQUEST);
    }

}
