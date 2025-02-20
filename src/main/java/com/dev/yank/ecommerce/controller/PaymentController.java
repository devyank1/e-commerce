package com.dev.yank.ecommerce.controller;

import com.dev.yank.ecommerce.dto.PaymentDTO;
import com.dev.yank.ecommerce.services.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDTO>> getPayments(Pageable pageable) {
        Page<PaymentDTO> payments = paymentService.getPayments(pageable);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO newPayment) {
        PaymentDTO payment = paymentService.createPayment(newPayment);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @RequestBody PaymentDTO updatePayment) {
        PaymentDTO payment = paymentService.updatePayment(id, updatePayment);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    // Payment Process
    @PostMapping("/process/{orderId}")
    public ResponseEntity<PaymentDTO> processPayment(@PathVariable Long orderId, @RequestBody PaymentDTO payment) {
        PaymentDTO processingPayment = paymentService.processPayment(orderId, payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(processingPayment);
    }

    @GetMapping("/status/{transactionId}")
    public ResponseEntity<String> verifyPaymentStatus(@PathVariable String transactionId) {
        String status = paymentService.getPaymentStatus(transactionId);
        return ResponseEntity.ok(status);
    }

    // Check Payment Status
    @GetMapping("/update-status/{transactionId}")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable String transactionId) {
        paymentService.updatePaymentStatus(transactionId);
        return ResponseEntity.ok("Payment Status updated successfully.");
    }
}
