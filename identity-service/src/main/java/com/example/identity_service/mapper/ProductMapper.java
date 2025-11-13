package com.example.identity_service.mapper;

import com.example.identity_service.dto.response.ColorResponse;
import com.example.identity_service.dto.response.ProductResponse;
import com.example.identity_service.dto.response.ProductVariantResponse;
import com.example.identity_service.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ColorMapper.class})
public interface ProductMapper {
    @Mapping(target = "variants", source = "variants")
    @Mapping(target = "productId", source = "productId")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "color", source = "color") // chỉ cần source = color
    @Mapping(target = "sizes", expression = "java(mapSizes(variant.getProductSizes()))")
    @Mapping(target = "imageUrls", expression = "java(mapImages(variant.getImages()))")
    ProductVariantResponse toProductVariantResponse(ProductVariant variant);

    // helper methods
    default List<String> mapSizes(List<ProductSize> sizes) {
        return sizes.stream().map(ProductSize::getName).toList();
    }

    default List<String> mapImages(List<Image> images) {
        return images.stream().map(Image::getUrl).toList();
    }

    // sử dụng ColorMapper
    default List<ColorResponse> mapColorToList(Color color) {
        return color == null ? List.of() : List.of(new ColorResponse(color.getColorId(), color.getCode(), color.getName()));
    }


}
