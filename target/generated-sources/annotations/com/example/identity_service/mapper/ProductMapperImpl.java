package com.example.identity_service.mapper;

import com.example.identity_service.dto.response.ProductResponse;
import com.example.identity_service.dto.response.ProductVariantResponse;
import com.example.identity_service.entity.Product;
import com.example.identity_service.entity.ProductVariant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T10:54:24+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.variants( productVariantListToProductVariantResponseList( product.getVariants() ) );
        productResponse.productId( product.getProductId() );
        productResponse.name( product.getName() );
        productResponse.price( product.getPrice() );
        productResponse.discountPercent( product.getDiscountPercent() );

        return productResponse.build();
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

    protected List<ProductVariantResponse> productVariantListToProductVariantResponseList(List<ProductVariant> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductVariantResponse> list1 = new ArrayList<ProductVariantResponse>( list.size() );
        for ( ProductVariant productVariant : list ) {
            list1.add( toProductVariantResponse( productVariant ) );
        }

        return list1;
    }
}
