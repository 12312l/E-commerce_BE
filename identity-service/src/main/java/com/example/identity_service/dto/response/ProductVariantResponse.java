package com.example.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse {
    Long variantId;
    Integer stockQuantity;
    List<ColorResponse> color;
    List<String> sizes;
    List<String> imageUrls;
}
