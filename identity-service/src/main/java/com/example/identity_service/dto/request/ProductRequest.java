package com.example.identity_service.dto.request;

import com.example.identity_service.dto.response.ProductVariantResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    String material;
    String description;
    String instruction;
    Double price;
    Integer discountPercent;
    Long genresId;
}
