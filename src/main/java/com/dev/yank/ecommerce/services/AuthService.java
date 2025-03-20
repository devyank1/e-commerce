package com.dev.yank.ecommerce.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AuthService {

    @Value("${keycloak.token-uri}")
    private String tokenUri;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public Map<String, Object> login(String username, String password) {
        WebClient webClient = WebClient.create();

        return webClient.post()
                .uri(tokenUri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(
                        "client_id" + clientId +
                                "&client_secret" + clientSecret +
                                "&grant_type=password" +
                                "&username=" + username +
                                "&password=" + password
                ).retrieve().bodyToMono(Map.class).block();
    }
}
