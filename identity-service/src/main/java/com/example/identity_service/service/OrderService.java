package com.example.identity_service.service;

import com.example.identity_service.dto.request.EditOrderRequest;
import com.example.identity_service.dto.request.OrderDetailRequest;
import com.example.identity_service.dto.request.OrderRequest;
import com.example.identity_service.dto.response.OrderDetailResponse;
import com.example.identity_service.dto.response.OrderResponse;
import com.example.identity_service.entity.*;
import com.example.identity_service.enums.OrderStatus;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.OrderDetailMapper;
import com.example.identity_service.mapper.OrderMapper;
import com.example.identity_service.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    UserRepository userRepository;
    AddressRepository addressRepository;
    OrderMapper orderMapper;
    OrderDetailMapper orderDetailMapper;
    ProductVariantRepository productVariantRepository;


    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        Address address = addressRepository.findById(orderRequest.getAddressId()).orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOTFOUND));

        Order order = orderMapper.toOrder(orderRequest, user, address);

        double total =0;

        for (OrderDetailRequest item : orderRequest.getItems()){
            ProductVariant productVariant = productVariantRepository.findById(item.getVariantId()).orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
            OrderDetail orderDetail = orderDetailMapper.toOrderDetail(item, productVariant, order);

            if(orderDetail.getQuantity()>productVariant.getStockQuantity()){
                throw new AppException(ErrorCode.INVALID_QUANTITY);
            }

            productVariant.setStockQuantity(productVariant.getStockQuantity()-orderDetail.getQuantity());
            productVariantRepository.save(productVariant);

            order.getOrderDetails().add(orderDetail);

            total+=orderDetail.getPrice()*orderDetail.getQuantity();
        }

        order.setTotalAmount(total+(order.getShippingFee()!=null ? order.getShippingFee(): 0));

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Transactional
    public List<OrderResponse> getMyOrder() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        List<Order> orders = orderRepository.findAllByUser_UserId(user.getUserId());

        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }
//
    @Transactional
    public OrderDetailResponse getOrderDetailResponse(Long orderId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        Order order = orderRepository.findByUser_UserIdAndOrderId(user.getUserId(), orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXSISTED));

        return orderMapper.toOrderDetailResponse(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        Order order = orderRepository.findByUser_UserIdAndOrderId(user.getUserId(), orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXSISTED));

        if(!order.getOrderStatus().equals(OrderStatus.PENDING)){
            throw new AppException(ErrorCode.ORDER_CANNOT_CANCEL);
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        restoreQuantityStock(order);

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse editOrder(EditOrderRequest editOrderRequest){
        Order order = orderRepository.findById(editOrderRequest.getOrderId()).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXSISTED));

        validateStatusTransition(order.getOrderStatus(), editOrderRequest.getOrderStatus());

        if(editOrderRequest.getOrderStatus().equals(OrderStatus.CANCELLED)){
            restoreQuantityStock(order);
        }

        order.setOrderStatus(editOrderRequest.getOrderStatus());
        order.setPaymentStatus(editOrderRequest.getPaymentStatus());

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    private void validateStatusTransition(OrderStatus oldStatus, OrderStatus newStatus) {

        if (oldStatus == newStatus) {
            return;
        }

        switch (oldStatus) {

            case PENDING:
                if (newStatus != OrderStatus.PROCESSING &&
                        newStatus != OrderStatus.CANCELLED) {
                    throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
                }
                break;

            case PROCESSING:
                if (newStatus != OrderStatus.SHIPPED) {
                    throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
                }
                break;

            case SHIPPED:
                if (newStatus != OrderStatus.DELIVERED) {
                    throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
                }
                break;

            case DELIVERED:
                throw new AppException(ErrorCode.INVALID_ORDER_STATUS);

            case CANCELLED:
                throw new AppException(ErrorCode.INVALID_ORDER_STATUS);

            case RETURNED:
                throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
    }

    public void restoreQuantityStock(Order order){
        for (OrderDetail detail : order.getOrderDetails()) {
            ProductVariant variant = detail.getProductVariant();
            variant.setStockQuantity(variant.getStockQuantity()+detail.getQuantity());
            productVariantRepository.save(variant);
        }
    }

}
