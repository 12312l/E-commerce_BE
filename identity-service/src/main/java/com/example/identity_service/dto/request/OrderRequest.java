package com.example.identity_service.dto.request;

import com.example.identity_service.enums.DeliveryMethod;
import com.example.identity_service.enums.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Double shippingFee;
    PaymentMethod paymentMethod;
    DeliveryMethod deliveryMethod;
    Long addressId;
    List<OrderDetailRequest> items;
}
