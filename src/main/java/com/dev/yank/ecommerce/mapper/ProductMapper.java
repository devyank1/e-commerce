package com.dev.yank.ecommerce.mapper;

import com.dev.yank.ecommerce.dto.ProductDTO;
import com.dev.yank.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category", target = "category")
    ProductDTO toDTO(Product product);

    @Mapping(source = "category", target = "category")
    Product toEntity(ProductDTO dto);
}
