package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.PaymentDTO;
import com.dev.yank.ecommerce.exception.OrderNotFoundException;
import com.dev.yank.ecommerce.exception.PaymentNotFoundException;
import com.dev.yank.ecommerce.mapper.PaymentMapper;
import com.dev.yank.ecommerce.model.Order;
import com.dev.yank.ecommerce.model.Payment;
import com.dev.yank.ecommerce.model.enums.PaymentStatus;
import com.dev.yank.ecommerce.repository.OrderRepository;
import com.dev.yank.ecommerce.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentService {

    // Injection
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;
    private final PayPalService payPalService;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, OrderRepository orderRepository, PayPalService payPalService) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.orderRepository = orderRepository;
        this.payPalService = payPalService;
    }

    // Methods
    public Page<PaymentDTO> getPayments(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAll(pageable);
        return payments.map(paymentMapper::toDTO);
    }

    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));
        return paymentMapper.toDTO(payment);
    }

    @Transactional
    public PaymentDTO createPayment(PaymentDTO newPayment) {
        Payment payment = paymentMapper.toEntity(newPayment);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toDTO(savedPayment);
    }

    @Transactional
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

    @Transactional
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException("Payment not found with ID: " + id);
        }
    }

    // Payment Process
    @Transactional
    public PaymentDTO processPayment(Long orderId, PaymentDTO payment) {

        if (paymentRepository.existsByOrderId(orderId)) {
            throw new IllegalStateException("There is already a payment for this Order.");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found."));

        String paypalPaymentId = payPalService.createPayment(payment.amount(), "USD");

        Payment newPayment = new Payment();
        newPayment.setAmount(payment.amount());
        newPayment.setPaymentDate(new Date());
        newPayment.setPaymentStatus(PaymentStatus.PENDING);
        newPayment.setTransactionId(paypalPaymentId);
        newPayment.setOrder(order);

        Payment savedPayment = paymentRepository.save(newPayment);
        return paymentMapper.toDTO(savedPayment);
    }

    public String getPaymentStatus(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .map(payment -> payment.getPaymentStatus().name())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found for Transaction: " + transactionId));
    }

    // Payment Update
    @Transactional
    public void updatePaymentStatus(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + transactionId));

        String paypalStatus = payPalService.getPaymentStatus(transactionId);

        if ("approved".equalsIgnoreCase(paypalStatus)) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
        } else if ("failed".equalsIgnoreCase(paypalStatus)) {
            payment.setPaymentStatus(PaymentStatus.FAILED);
        } else if ("pending".equalsIgnoreCase(paypalStatus)) {
            payment.setPaymentStatus(PaymentStatus.PENDING);
        } else if ("canceled".equalsIgnoreCase(paypalStatus)) {
            payment.setPaymentStatus(PaymentStatus.CANCELLED);
        }

        paymentRepository.save(payment);
    }

}
