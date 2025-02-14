package com.dev.yank.ecommerce.dto;

import com.dev.yank.ecommerce.model.enums.OrderStatus;

import java.util.Date;
import java.util.Set;

public record OrderDTO(Long id,
                       Date orderDate,
                       Double totalAmount,
                       OrderStatus orderStatus,
                       UserDTO user,
                       Set<OrderItemDTO> orderItem) {}
