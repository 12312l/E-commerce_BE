package com.example.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    Long orderDetailId;

    Integer quantity;

//    String size;
//
//    String colorName;
//
//    Double price;
//
//    Long variantId;

    String productName;
}
