package com.dev.yank.ecommerce.dto;

public record OrderItemDTO(Long id,
                           int quantity,
                           double price,
                           ProductDTO product,
                           OrderDTO order) {}
