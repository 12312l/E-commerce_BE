package com.example.identity_service.dto.response;

import com.example.identity_service.enums.DeliveryMethod;
import com.example.identity_service.enums.OrderStatus;
import com.example.identity_service.enums.PaymentMethod;
import com.example.identity_service.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long orderId;

    String orderCode;

    Double totalAmount;

    PaymentStatus paymentStatus;

    PaymentMethod paymentMethod;

    DeliveryMethod deliveryMethod;

    OrderStatus orderStatus;

    LocalDateTime createAt;

    String addressName;

    String fullname;

    List<OrderItemResponse> items;
}
