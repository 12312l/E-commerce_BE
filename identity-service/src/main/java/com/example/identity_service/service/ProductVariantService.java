package com.example.identity_service.service;

import com.example.identity_service.dto.request.ProductVariantRequest;
import com.example.identity_service.dto.response.ProductDetailResponse;
import com.example.identity_service.dto.response.ProductVariantResponse;
import com.example.identity_service.entity.Color;
import com.example.identity_service.entity.Product;
import com.example.identity_service.entity.ProductSize;
import com.example.identity_service.entity.ProductVariant;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.ProductVariantMapper;
import com.example.identity_service.repository.ColorRepository;
import com.example.identity_service.repository.ProductReponsitory;
import com.example.identity_service.repository.ProductSizeRepository;
import com.example.identity_service.repository.ProductVariantRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantService {
    ProductVariantMapper productVariantMapper;

    ProductVariantRepository productVariantRepository;

    ColorRepository colorRepository;

    ProductReponsitory productReponsitory;

    ProductSizeRepository productSizeRepository;

    @Transactional
    public ProductVariantResponse createVariant(ProductVariantRequest productVariantRequest){
        Color color = colorRepository.findById(productVariantRequest.getColorId())
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOTFOUND));

        Product product = productReponsitory.findById(productVariantRequest.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

        Optional<ProductVariant> existingVariant = productVariantRepository
                .findByProduct_ProductIdAndColor_ColorId(
                        productVariantRequest.getProductId(),
                        productVariantRequest.getColorId()
                );

        if (existingVariant.isPresent()) {
            throw new AppException(ErrorCode.VARIANT_DUPLICATE); // hoặc cập nhật nếu muốn
        }

        ProductVariant productVariant = productVariantMapper.toProductVariant(productVariantRequest);
        productVariant.setProduct(product);
        productVariant.setColor(color);

        // 1) Lưu variant trước
        ProductVariant savedVariant = productVariantRepository.save(productVariant);

        // 2) Lưu danh sách sizes
        if (productVariantRequest.getSizes() != null && !productVariantRequest.getSizes().isEmpty()) {
            List<ProductSize> sizes = productVariantRequest.getSizes().stream()
                    .map(sizeName -> ProductSize.builder()
                            .name(sizeName)
                            .productVariant(savedVariant)
                            .build()
                    ).toList();

            productSizeRepository.saveAll(sizes);
            savedVariant.setProductSizes(sizes);
        }

        return productVariantMapper.toProductVariantResponse(savedVariant);
    }
}
