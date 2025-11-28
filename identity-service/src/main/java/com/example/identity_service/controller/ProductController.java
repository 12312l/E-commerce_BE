package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.PriceFilterRequest;
import com.example.identity_service.dto.request.ProductRequest;
import com.example.identity_service.dto.response.ProductDetailResponse;
import com.example.identity_service.dto.response.ProductResponse;
import com.example.identity_service.service.ProductService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PostMapping("/add-product")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<ProductResponse> addProduct(ProductRequest productRequest){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(productRequest))
                .build();
    }

    @GetMapping
    ApiResponse<Page<ProductResponse>> getAllProduct(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "24") int size
    ){
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.getAllProduct(page-1, size))
                .build();
    }

    @GetMapping("/best-seller")
    ApiResponse<Page<ProductResponse>> getBestSeller(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "24") int size
    ){
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.getBestSeller(page-1, size))
                .build();
    }

    @GetMapping("/genres/{genresId}")
    ApiResponse<Page<ProductResponse>> getProductByGenresId(
            @PathVariable("genresId") Long genresId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "24") int size
    ){
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.getProductByGenresId(genresId, page-1, size))
                .build();
    }

    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> getProductById(@RequestParam("productId") Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .build();
    }

    @GetMapping("/{productId}/detail")
    ApiResponse<ProductDetailResponse> getProductDetailById(@RequestParam("productId") Long id){
        return ApiResponse.<ProductDetailResponse>builder()
                .result(productService.getProductDetailById(id))
                .build();
    }

    @GetMapping("/newProduct")
    ApiResponse<Page<ProductResponse>> getNewProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "24") int size
    ){
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.getNewProduct(page-1, size))
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
