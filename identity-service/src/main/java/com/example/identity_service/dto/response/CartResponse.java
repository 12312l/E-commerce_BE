package com.example.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    Long cartId;
    String size;
    Integer quantity;
    Double totalPrice;

    Long userId;

    Long productId;
    String productName;
    String productPrice;

    Long variantId;
    String colorName;
    String urlImage;
}
