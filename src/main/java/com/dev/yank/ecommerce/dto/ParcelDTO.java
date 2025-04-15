package com.dev.yank.ecommerce.dto;

public record ParcelDTO(String length,
                        String width,
                        String height,
                        String distanceUnit,
                        String weight,
                        String massUnit) {
}
