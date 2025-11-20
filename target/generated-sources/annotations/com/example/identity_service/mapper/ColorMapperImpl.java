package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.ColorRequest;
import com.example.identity_service.dto.response.ColorResponse;
import com.example.identity_service.entity.Color;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T21:09:27+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ColorMapperImpl implements ColorMapper {

    @Override
    public Color toColor(ColorRequest request) {
        if ( request == null ) {
            return null;
        }

        Color color = new Color();

        color.setCode( request.getCode() );
        color.setName( request.getName() );

        return color;
    }

    @Override
    public ColorResponse toColorResponse(Color color) {
        if ( color == null ) {
            return null;
        }

        ColorResponse.ColorResponseBuilder colorResponse = ColorResponse.builder();

        colorResponse.colorId( color.getColorId() );
        colorResponse.code( color.getCode() );
        colorResponse.name( color.getName() );

        return colorResponse.build();
    }
}
