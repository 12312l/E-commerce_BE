package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.OrderDetailRequest;
import com.example.identity_service.dto.response.OrderItemResponse;
import com.example.identity_service.entity.Color;
import com.example.identity_service.entity.Order;
import com.example.identity_service.entity.OrderDetail;
import com.example.identity_service.entity.Product;
import com.example.identity_service.entity.ProductVariant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-24T17:44:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public OrderDetail toOrderDetail(OrderDetailRequest req, ProductVariant variant, Order order) {
        if ( req == null && variant == null && order == null ) {
            return null;
        }

        OrderDetail orderDetail = new OrderDetail();

        if ( req != null ) {
            orderDetail.setQuantity( req.getQuantity() );
            orderDetail.setSize( req.getSize() );
        }
        if ( variant != null ) {
            orderDetail.setColorName( variantColorName( variant ) );
            orderDetail.setPrice( variantProductPrice( variant ) );
            orderDetail.setProductVariant( variant );
        }
        orderDetail.setOrder( order );

        return orderDetail;
    }

    @Override
    public OrderItemResponse toOrderItemResponse(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }

        OrderItemResponse.OrderItemResponseBuilder orderItemResponse = OrderItemResponse.builder();

        orderItemResponse.orderDetailId( orderDetail.getOrderDetailId() );
        orderItemResponse.quantity( orderDetail.getQuantity() );

        orderItemResponse.productName( mapProductName(orderDetail) );

        return orderItemResponse.build();
    }

    private String variantColorName(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }
        Color color = productVariant.getColor();
        if ( color == null ) {
            return null;
        }
        String name = color.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Double variantProductPrice(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }
        Product product = productVariant.getProduct();
        if ( product == null ) {
            return null;
        }
        Double price = product.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }
}
