package com.dev.yank.ecommerce.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "order-events", groupId = "ecommerce-group")
    public void consumeOrderEvent(ConsumerRecord<String, String> record) {
        System.out.println("Received Order Event:" + record.value());
    }

    @KafkaListener(topics = "payment-events", groupId = "ecommerce-group")
    public void consumePaymentEvent(ConsumerRecord<String, String> record) {
        System.out.println("Received Payement Event:" + record.value());
    }
}
