package com.dev.yank.ecommerce.mapper;

import com.dev.yank.ecommerce.dto.CategoryDTO;
import com.dev.yank.ecommerce.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "products", target = "products")
    CategoryDTO toDTO(Category category);

    @Mapping(source = "products", target = "products")
    Category toEntity(CategoryDTO dto);
}
