package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.ColorRequest;
import com.example.identity_service.dto.response.ColorResponse;
import com.example.identity_service.entity.Color;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    Color toColor(ColorRequest request);

    @Mapping(source = "colorId", target = "colorId")
    ColorResponse toColorResponse(Color color);
}
