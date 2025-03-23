package com.CloneShopee.controllers.User;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.UserBean;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.VoucherShop;
import com.CloneShopee.models.VoucherShopAccount;
import com.CloneShopee.models.VoucherShopAccountId;
import com.CloneShopee.models.VoucherShopItem;
import com.CloneShopee.services.sale.VoucherService;

@RestController
@CrossOrigin("*")
public class UserVoucherController {

    @Autowired
    UserBean userBean;

    @Autowired
    private VoucherService voucherService;

    @PostMapping("/user/voucher/add")
    public ResponseEntity<Object> reciveVoucher(@RequestParam("voucherId") Integer voucherId) {
        VoucherShop voucher = voucherService.getVoucherByIdAndStarting(voucherId);
        if (voucher != null) {
            VoucherShopAccount voucherShopAccount = new VoucherShopAccount();
            voucherShopAccount.setQuantityUsed(0);
            voucherShopAccount.setId(new VoucherShopAccountId(voucherId, userBean.getId()));
            return new ResponseEntity<>(new BaseRespone(null, "Success !!!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Not find voucher !!!"), HttpStatus.BAD_REQUEST);
    }

}
