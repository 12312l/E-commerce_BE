package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.OrderRequest;
import com.example.identity_service.dto.response.OrderDetailResponse;
import com.example.identity_service.dto.response.OrderResponse;
import com.example.identity_service.entity.Address;
import com.example.identity_service.entity.Order;
import com.example.identity_service.entity.OrderDetail;
import com.example.identity_service.entity.User;
import com.example.identity_service.enums.DeliveryMethod;
import com.example.identity_service.enums.OrderStatus;
import com.example.identity_service.enums.PaymentMethod;
import com.example.identity_service.enums.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        imports = {PaymentMethod.class, DeliveryMethod.class, PaymentStatus.class, OrderStatus.class},
        uses = {OrderDetailMapper.class}
)
public interface OrderMapper {
    @Mapping(target = "shippingFee", source = "request.shippingFee")
//    @Mapping(target = "paymentMethod", expression = "java(PaymentMethod.valueOf(request.getPaymentMethod()))")
//    @Mapping(target = "deliveryMethod", expression = "java(DeliveryMethod.valueOf(request.getDeliveryMethod()))")
    @Mapping(target = "paymentMethod", source = "request.paymentMethod")
    @Mapping(target = "deliveryMethod", source = "request.deliveryMethod")
    @Mapping(target = "paymentStatus", expression = "java(PaymentStatus.UNPAID)")
    @Mapping(target = "orderStatus", expression = "java(OrderStatus.PENDING)")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "orderCode", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    Order toOrder(OrderRequest request, User user, Address address);

    @Mapping(target = "items", source = "orderDetails")
    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "items", source = "orderDetails")
    @Mapping(target = "addressName", expression = "java(mapAddressName(order))")
    @Mapping(target = "fullname", expression = "java(mapAddressFullname(order))")
    OrderDetailResponse toOrderDetailResponse(Order order);

    default String mapAddressName(Order od) {
        return od.getAddress() != null
                ? od.getAddress().getDetailAddress()
                : null;
    }

    default String mapAddressFullname(Order od) {
        return od.getAddress() != null
                ? od.getAddress().getFullname()
                : null;
    }

}
