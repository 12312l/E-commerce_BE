package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.ProductVariantRequest;
import com.example.identity_service.dto.response.ProductVariantResponse;
import com.example.identity_service.service.ProductVariantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/variant")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantController {
    ProductVariantService productVariantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<ProductVariantResponse> addVariant(@RequestBody ProductVariantRequest productVariantRequest){
        return ApiResponse.<ProductVariantResponse>builder()
                .result(productVariantService.createVariant(productVariantRequest))
                .build();
    }
}
