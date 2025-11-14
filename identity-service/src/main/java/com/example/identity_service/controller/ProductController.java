package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.PriceFilterRequest;
import com.example.identity_service.dto.response.ProductResponse;
import com.example.identity_service.service.ProductService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping
    ApiResponse<List<ProductResponse>> getAllProduct(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProduct())
                .build();
    }

    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> getProductById(@RequestParam("productId") Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .build();
    }

    @GetMapping("/newProduct")
    ApiResponse<List<ProductResponse>> getNewProduct(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getNewProduct())
                .build();
    }

    @GetMapping("/search")
    ApiResponse<List<ProductResponse>> searchProducts(@RequestParam("keyword") String keyword){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.searchProduct(keyword))
                .build();
    }

    //Api filter price after search product
    @PostMapping("/filterPrice")
    ApiResponse<List<ProductResponse>> filterByPrice(@RequestBody PriceFilterRequest priceFilterRequest){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.filterByPrice(priceFilterRequest))
                .build();
    }


}
