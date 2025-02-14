package com.dev.yank.ecommerce.dto;

import com.dev.yank.ecommerce.model.enums.PaymentStatus;

import java.util.Date;

public record PaymentDTO(Long id,
                         double amount,
                         Date paymentDate,
                         PaymentStatus paymentStatus,
                         String transactionId,
                         OrderDTO order) {}
