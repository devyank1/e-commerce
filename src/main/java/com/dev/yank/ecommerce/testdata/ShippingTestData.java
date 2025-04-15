package com.dev.yank.ecommerce.testdata;

import com.dev.yank.ecommerce.dto.AddressDTO;
import com.dev.yank.ecommerce.dto.ParcelDTO;
import com.dev.yank.ecommerce.dto.ShipmentRequestDTO;

import java.util.List;

public class ShippingTestData {

    public static ShipmentRequestDTO buildTestPayload(){
        AddressDTO from = new AddressDTO(
                "Yan", "123 Rua Grande", "São Luís", "01000-000", "BR"
        );

        AddressDTO to = new AddressDTO(
                "Carlos", "Av. Mirassolândia", "São José do Rio Preto", "02000-000", "BR"
        );

        ParcelDTO parcel = new ParcelDTO(
                "5", "5", "5", "cm", "3", "kg"
        );

        return new ShipmentRequestDTO(from, to, List.of(parcel), false);
    }
}
