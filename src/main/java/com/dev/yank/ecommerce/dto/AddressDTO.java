package com.dev.yank.ecommerce.dto;

public record AddressDTO(String name,
                         String street,
                         String city,
                         String zip,
                         String country) {
}
