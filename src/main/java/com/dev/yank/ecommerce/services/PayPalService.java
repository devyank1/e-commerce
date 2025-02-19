package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.config.paypal.PayPalConfig;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class PayPalService {

    private static final String PAYPAL_API_BASE = "https://api-m.sandbox.paypal.com";

    private final RestTemplate restTemplate;
    private final String clientId;
    private final String secret;

    public PayPalService(PayPalConfig payPalConfig) {
        this.clientId = payPalConfig.getClientId();
        this.secret = payPalConfig.getSecret();
        this.restTemplate = new RestTemplate();
    }

    public String getAccessToken() {
        String authUrl = PAYPAL_API_BASE + "/oauth2/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, secret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(authUrl, HttpMethod.POST, request, Map.class);

        return response.getBody().get("access-token").toString();
    }

    public String createPayment(Double amount, String currency) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> paymentDetails = new HashMap<>();
        paymentDetails.put("intent", "sale");

        Map<String, Object> transaction = new HashMap<>();
        transaction.put("amount", Map.of("total", amount.toString(), "currency", currency));

        paymentDetails.put("transactions", new Object[]{transaction});
        paymentDetails.put("redirect_urls", Map.of(
                "return_url", "http://localhost:8081/payment/success",
                "cancel_url", "http://localhost:8081/payment/cancel"
        ));
        paymentDetails.put("payer", Map.of("payment_method", "paypal"));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentDetails, headers);

        ResponseEntity<Map> response = restTemplate.exchange(PAYPAL_API_BASE+ "/payments/payment", HttpMethod.POST, request, Map.class);

        return response.getBody().get("id").toString();
    }

    public String getPaymentStatus(String paymentId) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                PAYPAL_API_BASE + "/payments/payment/" + paymentId,
                HttpMethod.GET,
                request,
                Map.class
        );

        return response.getBody().get("state").toString();
    }
}
