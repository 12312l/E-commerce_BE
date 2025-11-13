package com.example.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
    Long productId;
    String name;
    String material;
    String description;
    String instruction;
    Double price;
    Integer discountPercent;
    List<ProductVariantResponse> variants;
}
