package com.dev.yank.ecommerce.repository;

import com.dev.yank.ecommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByOrderId(Long orderId);

    Optional<Payment> findByTransactionId(String transactionId);
}
