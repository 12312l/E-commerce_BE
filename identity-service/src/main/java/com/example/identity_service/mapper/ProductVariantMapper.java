package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.ProductVariantRequest;
import com.example.identity_service.dto.response.ColorResponse;
import com.example.identity_service.dto.response.ProductVariantResponse;
import com.example.identity_service.entity.Color;
import com.example.identity_service.entity.Image;
import com.example.identity_service.entity.ProductSize;
import com.example.identity_service.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    ProductVariant toProductVariant(ProductVariantRequest productVariantRequest);

    @Mapping(target = "color", source = "color") // chỉ cần source = color
    @Mapping(target = "sizes", expression = "java(mapSizes(variant.getProductSizes()))")
    @Mapping(target = "imageUrls", expression = "java(mapImages(variant.getImages()))")
    ProductVariantResponse toProductVariantResponse(ProductVariant variant);

    default List<String> mapSizes(List<ProductSize> sizes) {
        return sizes == null ? List.of()
                : sizes.stream()
                .map(ProductSize::getName)
                .toList();
    }

    default List<String> mapImages(List<Image> images) {
        if (images == null || images.isEmpty()) {
            return List.of(); // hoặc Collections.emptyList()
        }
        return images.stream()
                .map(Image::getUrl)
                .toList();
    }



    // sử dụng ColorMapper
    default List<ColorResponse> mapColorToList(Color color) {
        return color == null ? List.of() : List.of(new ColorResponse(color.getColorId(), color.getCode(), color.getName()));
    }
}
