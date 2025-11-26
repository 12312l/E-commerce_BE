package com.example.identity_service.dto.request;

import com.example.identity_service.enums.OrderStatus;
import com.example.identity_service.enums.PaymentMethod;
import com.example.identity_service.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditOrderRequest {
    Long orderId;
    OrderStatus orderStatus;
    PaymentStatus paymentStatus;
}
