package com.dev.yank.ecommerce.dto;

public record ProductDTO(Long id,
                         String name,
                         String description,
                         double price,
                         int stockQuantity,
                         CategoryDTO category) {}
