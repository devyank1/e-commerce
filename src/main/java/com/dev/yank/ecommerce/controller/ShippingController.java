package com.dev.yank.ecommerce.controller;

import com.dev.yank.ecommerce.dto.ShipmentRequestDTO;
import com.dev.yank.ecommerce.services.ShippingService;
import com.dev.yank.ecommerce.testdata.ShippingTestData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testShippo() {
        ShipmentRequestDTO payload = ShippingTestData.buildTestPayload();
        String response = shippingService.createShipment(payload);
        return ResponseEntity.ok(response);
    }
}
