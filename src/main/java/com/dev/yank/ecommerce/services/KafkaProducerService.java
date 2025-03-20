package com.dev.yank.ecommerce.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(String orderId) {
        kafkaTemplate.send("order-events", orderId);
    }

    public void sendPaymentEvent(String paymentId) {
        kafkaTemplate.send("payment-events", paymentId);
    }
}
