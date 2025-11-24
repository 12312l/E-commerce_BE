package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.OrderDetailRequest;
import com.example.identity_service.dto.response.OrderItemResponse;
import com.example.identity_service.entity.Order;
import com.example.identity_service.entity.OrderDetail;
import com.example.identity_service.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    @Mapping(target = "quantity", source = "req.quantity")
    @Mapping(target = "size", source = "req.size")
    @Mapping(target = "colorName", source = "variant.color.name")
    @Mapping(target = "price", source = "variant.product.price")
    @Mapping(target = "productVariant", source = "variant")
    @Mapping(target = "order", source = "order")
    OrderDetail toOrderDetail(OrderDetailRequest req, ProductVariant variant, Order order);


//    @Mapping(target = "variantId", source = "productVariant.variantId")
//    @Mapping(target = "productName", source = "productVariant.product.name")
//    OrderItemResponse toOrderItemResponse(OrderDetail orderDetail);

    @Mapping(target = "productName", expression = "java(mapProductName(orderDetail))")
    @Mapping(target = "orderDetailId", source = "orderDetailId")
    @Mapping(target = "quantity", source = "quantity")
    OrderItemResponse toOrderItemResponse(OrderDetail orderDetail);


    default Long mapVariantId(OrderDetail od) {
        return od.getProductVariant() != null ? od.getProductVariant().getVariantId() : null;
    }

    default String mapProductName(OrderDetail od) {
        return od.getProductVariant() != null && od.getProductVariant().getProduct() != null
                ? od.getProductVariant().getProduct().getName()
                : null;
    }


}
