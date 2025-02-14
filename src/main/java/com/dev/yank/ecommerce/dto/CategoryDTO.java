package com.dev.yank.ecommerce.dto;

import com.dev.yank.ecommerce.model.Product;

import java.util.Set;

public record CategoryDTO(Long id,
                          String name,
                          Set<Product> products) {}
