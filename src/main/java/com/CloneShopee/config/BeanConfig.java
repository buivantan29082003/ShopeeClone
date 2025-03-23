package com.CloneShopee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.Bean.UserBean;

@Configuration
public class BeanConfig {
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ShopBean account() {
        return new ShopBean();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserBean userBean() {
        return new UserBean();
    }
}
