package com.dev.yank.ecommerce.config.paypal;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayPalConfig {

    @Value("${server.paypal.client-id}")
    private String clientId;

    @Value("${server.paypal.secret}")
    private String clientSecret;

    @Value("${server.paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext()throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        apiContext.setConfigurationMap(paypalSdkConfig());
        return apiContext;
    }

    private Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
    }
}
