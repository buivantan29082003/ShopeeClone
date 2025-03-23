package com.CloneShopee.Bean;

import com.CloneShopee.models.Account;
import com.CloneShopee.models.Shop;



public class ShopBean {
    private Shop shop = new Shop(1);
    private Integer accountId = 1;
    private Account account = new Account(1);

    public ShopBean() {

    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
