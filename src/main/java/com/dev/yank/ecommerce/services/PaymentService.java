package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.PaymentDTO;
import com.dev.yank.ecommerce.exception.PaymentNotFoundException;
import com.dev.yank.ecommerce.mapper.PaymentMapper;
import com.dev.yank.ecommerce.model.Payment;
import com.dev.yank.ecommerce.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    // Injection
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    // Methods
    public List<PaymentDTO> getPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDTO).toList();
    }

    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));
        return paymentMapper.toDTO(payment);
    }

    public PaymentDTO createPayment(PaymentDTO newPayment) {
        Payment payment = paymentMapper.toEntity(newPayment);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toDTO(savedPayment);
    }

    public PaymentDTO updatePayment(Long id, PaymentDTO updatePayment) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));

        existingPayment.setPaymentDate(updatePayment.paymentDate());
        existingPayment.setPaymentStatus(updatePayment.paymentStatus());
        existingPayment.setAmount(updatePayment.amount());
        existingPayment.setTransactionId(updatePayment.transactionId());

        Payment updatedPayment = paymentRepository.save(existingPayment);
        return paymentMapper.toDTO(updatedPayment);
    }

    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException("Payment not found with ID: " + id);
        }
    }
}
