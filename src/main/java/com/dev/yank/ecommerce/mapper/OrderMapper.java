package com.dev.yank.ecommerce.mapper;

import com.dev.yank.ecommerce.dto.OrderDTO;
import com.dev.yank.ecommerce.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class, OrderItemMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "orderItem", target = "orderItem")
    OrderDTO toDTO(Order order);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "orderItem", target = "orderItem")
    Order toEntity(OrderDTO dto);
}
