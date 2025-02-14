package com.dev.yank.ecommerce.mapper;

import com.dev.yank.ecommerce.dto.PaymentDTO;
import com.dev.yank.ecommerce.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(source = "order", target = "order")
    PaymentDTO toDTO(Payment payment);

    @Mapping(source = "order", target = "order")
    Payment toEntity(PaymentDTO dto);
}
