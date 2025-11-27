package com.example.identity_service.controller;


import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.response.ProductResponse;
import com.example.identity_service.service.FavouriteProductService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourite")
@RequiredArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavouriteProductController {
    FavouriteProductService favouriteProductService;

    @PostMapping("/add/{productId}")
    ApiResponse<String> addFavouriteProduct(@PathVariable("productId") Long productId){
        return ApiResponse.<String>builder()
                .result(favouriteProductService.addFavouriteProduct(productId))
                .build();
    }

    @GetMapping("/my-favourite")
    ApiResponse<List<ProductResponse>> myFavourite(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(favouriteProductService.getAllFavouriteOfUser())
                .build();
    }

    @DeleteMapping("/delete/{favouriteId}")
    ApiResponse<String> deleteFavourite(@PathVariable("favouriteId") Long favouriteId){
        return ApiResponse.<String>builder()
                .result(favouriteProductService.deleteFavourite(favouriteId))
                .build();
    }
}
