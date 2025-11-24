package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.OrderRequest;
import com.example.identity_service.dto.response.OrderDetailResponse;
import com.example.identity_service.dto.response.OrderItemResponse;
import com.example.identity_service.dto.response.OrderResponse;
import com.example.identity_service.entity.Address;
import com.example.identity_service.entity.Order;
import com.example.identity_service.entity.OrderDetail;
import com.example.identity_service.entity.User;
import com.example.identity_service.enums.DeliveryMethod;
import com.example.identity_service.enums.OrderStatus;
import com.example.identity_service.enums.PaymentMethod;
import com.example.identity_service.enums.PaymentStatus;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-24T17:26:06+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Order toOrder(OrderRequest request, User user, Address address) {
        if ( request == null && user == null && address == null ) {
            return null;
        }

        Order order = new Order();

        if ( request != null ) {
            order.setShippingFee( request.getShippingFee() );
            order.setPaymentMethod( request.getPaymentMethod() );
            order.setDeliveryMethod( request.getDeliveryMethod() );
        }
        if ( user != null ) {
            order.setUser( user );
            order.setCreateAt( user.getCreateAt() );
        }
        order.setAddress( address );
        order.setPaymentStatus( PaymentStatus.UNPAID );
        order.setOrderStatus( OrderStatus.PENDING );

        return order;
    }

    @Override
    public OrderResponse toOrderResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();

        orderResponse.items( orderDetailListToOrderItemResponseList( order.getOrderDetails() ) );
        orderResponse.orderId( order.getOrderId() );
        orderResponse.orderCode( order.getOrderCode() );
        orderResponse.totalAmount( order.getTotalAmount() );
        orderResponse.orderStatus( order.getOrderStatus() );
        orderResponse.createAt( order.getCreateAt() );

        return orderResponse.build();
    }

    @Override
    public OrderDetailResponse toOrderDetailResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDetailResponse.OrderDetailResponseBuilder orderDetailResponse = OrderDetailResponse.builder();

        orderDetailResponse.items( orderDetailListToOrderItemResponseList( order.getOrderDetails() ) );
        orderDetailResponse.orderId( order.getOrderId() );
        orderDetailResponse.orderCode( order.getOrderCode() );
        orderDetailResponse.totalAmount( order.getTotalAmount() );
        orderDetailResponse.paymentStatus( order.getPaymentStatus() );
        orderDetailResponse.paymentMethod( order.getPaymentMethod() );
        orderDetailResponse.deliveryMethod( order.getDeliveryMethod() );
        orderDetailResponse.orderStatus( order.getOrderStatus() );
        orderDetailResponse.createAt( order.getCreateAt() );

        orderDetailResponse.addressName( mapAddressName(order) );
        orderDetailResponse.fullname( mapAddressFullname(order) );

        return orderDetailResponse.build();
    }

    protected List<OrderItemResponse> orderDetailListToOrderItemResponseList(List<OrderDetail> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemResponse> list1 = new ArrayList<OrderItemResponse>( list.size() );
        for ( OrderDetail orderDetail : list ) {
            list1.add( orderDetailMapper.toOrderItemResponse( orderDetail ) );
        }

        return list1;
    }
}
