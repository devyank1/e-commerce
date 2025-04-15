package com.dev.yank.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ShipmentRequestDTO(
@JsonProperty("address_from") AddressDTO addressFrom,
@JsonProperty("address_to") AddressDTO addressTo,
List<ParcelDTO> parcels,
boolean async
) {}
