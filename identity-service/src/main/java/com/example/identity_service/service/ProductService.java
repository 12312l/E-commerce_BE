package com.example.identity_service.service;

import com.example.identity_service.dto.request.PriceFilterRequest;
import com.example.identity_service.dto.response.ProductResponse;
import com.example.identity_service.entity.Product;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.ProductMapper;
import com.example.identity_service.repository.ProductReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductReponsitory productReponsitory;


    ProductMapper productMapper;

    public List<ProductResponse> getAllProduct(){
        return productReponsitory.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        return productMapper.toProductResponse(productReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND)));
    }

    public List<ProductResponse> getNewProduct() {
        return productReponsitory.findAllByOrderByCreateAtDesc()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }



    public List<ProductResponse> searchProduct(String keyword){
        if(keyword ==null || keyword.trim().isEmpty()){
            return List.of();
        }

        return productReponsitory.searchProducts(keyword.trim())
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    //Filter following by price product
    public List<ProductResponse> filterByPrice(PriceFilterRequest priceFilterRequest){
        if(priceFilterRequest.getProductIds() == null || priceFilterRequest.getProductIds().isEmpty()){
            return List.of();
        }

        return productReponsitory.filterByPrice(priceFilterRequest.getProductIds(), priceFilterRequest.getMinPrice(), priceFilterRequest.getMaxPrice())
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }


}
