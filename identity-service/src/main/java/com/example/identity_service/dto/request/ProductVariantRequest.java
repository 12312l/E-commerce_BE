package com.example.identity_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.engine.jdbc.Size;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantRequest {
    Integer stockQuantity;
    Long colorId;
    List<String> sizes;
    Long productId;
}
