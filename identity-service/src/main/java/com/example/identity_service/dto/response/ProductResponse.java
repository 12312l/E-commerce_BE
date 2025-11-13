package com.example.identity_service.dto.response;

import com.example.identity_service.entity.ProductVariant;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long productId;
    String name;
    Double price;
    Integer discountPercent;
    List<ProductVariantResponse> variants;
}
