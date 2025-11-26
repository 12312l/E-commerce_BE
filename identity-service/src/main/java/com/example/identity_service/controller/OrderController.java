package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.EditOrderRequest;
import com.example.identity_service.dto.request.OrderRequest;
import com.example.identity_service.dto.response.OrderDetailResponse;
import com.example.identity_service.dto.response.OrderResponse;
import com.example.identity_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {
    OrderService orderService;

    @PostMapping("/add-order")
    ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(orderRequest))
                .build();
    }

    @GetMapping("/my-order")
    ApiResponse<List<OrderResponse>> getMyOrder(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getMyOrder())
                .build();
    }

    @GetMapping("/{orderId}")
    ApiResponse<OrderDetailResponse> getDetailOrder(@PathVariable("orderId") Long orderId){
        return ApiResponse.<OrderDetailResponse>builder()
                .result(orderService.getOrderDetailResponse(orderId))
                .build();
    }

    @PatchMapping("/{orderId}/cancel")
    ApiResponse<OrderResponse> cancelOrder(@PathVariable Long orderId){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.cancelOrder(orderId))
                .build();
    }


    @PatchMapping("/edit-order")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<OrderResponse> editOrder(@RequestBody EditOrderRequest editOrderRequest){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.editOrder(editOrderRequest))
                .build();
    }
}
