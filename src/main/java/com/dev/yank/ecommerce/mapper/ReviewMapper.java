package com.dev.yank.ecommerce.mapper;

import com.dev.yank.ecommerce.dto.ReviewDTO;
import com.dev.yank.ecommerce.model.Product;
import com.dev.yank.ecommerce.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, Product.class})
public interface ReviewMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "product", target = "product")
    ReviewDTO toDTO(Review review);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "product", target = "product")
    Review toEntity(ReviewDTO dto);
}
