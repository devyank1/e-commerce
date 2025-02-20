package com.dev.yank.ecommerce.config.paypal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
public class PayPalConfig {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.secret}")
    private String secret;

    public String getClientId() {
        return clientId;
    }

    public String getSecret() {
        return secret;
    }
}
