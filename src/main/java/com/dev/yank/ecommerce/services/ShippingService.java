package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.ShipmentRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShippingService {

    private final RestTemplate restTemplate;
    private final String SHIPPO_API = "https://api.goshippo.com";
    @Value("${shippo.api.key}")
    private String shippoAiKey;

    public ShippingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createShipment(ShipmentRequestDTO shipmentPayload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(shippoAiKey, "");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ShipmentRequestDTO> entity = new HttpEntity<>(shipmentPayload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                SHIPPO_API + "/shipments/",
                entity,
                String.class
        );

        return response.getBody();
    }
}
