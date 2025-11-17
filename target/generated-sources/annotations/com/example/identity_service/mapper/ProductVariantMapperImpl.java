package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.ProductVariantRequest;
import com.example.identity_service.dto.response.ProductVariantResponse;
import com.example.identity_service.entity.ProductVariant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-17T22:23:15+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ProductVariantMapperImpl implements ProductVariantMapper {

    @Override
    public ProductVariant toProductVariant(ProductVariantRequest productVariantRequest) {
        if ( productVariantRequest == null ) {
            return null;
        }

        ProductVariant.ProductVariantBuilder productVariant = ProductVariant.builder();

        productVariant.stockQuantity( productVariantRequest.getStockQuantity() );

        return productVariant.build();
    }

    @Override
    public ProductVariantResponse toProductVariantResponse(ProductVariant variant) {
        if ( variant == null ) {
            return null;
        }

        ProductVariantResponse.ProductVariantResponseBuilder productVariantResponse = ProductVariantResponse.builder();

        productVariantResponse.color( mapColorToList( variant.getColor() ) );
        productVariantResponse.variantId( variant.getVariantId() );
        productVariantResponse.stockQuantity( variant.getStockQuantity() );

        productVariantResponse.sizes( mapSizes(variant.getProductSizes()) );
        productVariantResponse.imageUrls( mapImages(variant.getImages()) );

        return productVariantResponse.build();
    }
}
