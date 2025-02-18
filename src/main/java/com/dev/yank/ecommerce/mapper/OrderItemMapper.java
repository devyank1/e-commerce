package com.dev.yank.ecommerce.mapper;

import com.dev.yank.ecommerce.dto.OrderItemDTO;
import com.dev.yank.ecommerce.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "product", target = "product")
    @Mapping(source = "order", target = "order", ignore = true)
    OrderItemDTO toDTO(OrderItem orderItem);

    @Mapping(source = "product", target = "product")
    @Mapping(source = "order", target = "order", ignore = true)
    OrderItem toEntity(OrderItemDTO dto);
}
