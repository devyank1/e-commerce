package com.dev.yank.ecommerce.dto;


public record ReviewDTO(Long id,
                        int rating,
                        String comment,
                        UserDTO user,
                        ProductDTO product) {}
