package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.CartRequest;
import com.example.identity_service.dto.response.CartResponse;
import com.example.identity_service.service.CartService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @PostMapping("/add")
    ApiResponse<CartResponse> addCart(@RequestBody CartRequest cartRequest){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.addCart(cartRequest))
                .build();
    }

    @GetMapping("/myCart")
    ApiResponse<List<CartResponse>> myCart(){
        return ApiResponse.<List<CartResponse>>builder()
                .result(cartService.myCart())
                .build();
    }


    @PutMapping("/update")
    ApiResponse<CartResponse> updateCart(
            @RequestParam Long cartId,
            @RequestParam String action
    ){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.updateCart(cartId, action))
                .build();
    }



}
