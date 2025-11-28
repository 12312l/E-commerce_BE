package com.example.identity_service.service;

import com.example.identity_service.dto.request.PriceFilterRequest;
import com.example.identity_service.dto.request.ProductRequest;
import com.example.identity_service.dto.response.ProductDetailResponse;
import com.example.identity_service.dto.response.ProductResponse;
import com.example.identity_service.entity.Genres;
import com.example.identity_service.entity.Order;
import com.example.identity_service.entity.Product;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.ProductMapper;
import com.example.identity_service.repository.GenresRepository;
import com.example.identity_service.repository.OrderRepository;
import com.example.identity_service.repository.ProductReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductReponsitory productReponsitory;


    ProductMapper productMapper;

    GenresRepository genresRepository;

    OrderRepository orderRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Genres genre = genresRepository.findById(productRequest.getGenresId())
                .orElseThrow(() -> new AppException(ErrorCode.GENRES_NOTFOUND));

        Product product = productMapper.toProduct(productRequest);

        product.setGenres(genre);


        return productMapper.toProductResponse(productReponsitory.save(product));
    }

    public Page<ProductResponse> getAllProduct(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return productReponsitory.findAll(pageable)
                .map(productMapper::toProductResponse);
    }

    public Page<ProductResponse> getNewProduct(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);

        return productReponsitory.findAllByOrderByCreateAtDesc(pageable)
                .map(productMapper::toProductResponse);
    }

    public Page<ProductResponse> getBestSeller(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Product> products = orderRepository.findBestSellingProducts(pageable);

        return products.map(productMapper::toProductResponse);
    }

    public Page<ProductResponse> getProductByGenresId(Long genresId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return productReponsitory.findAllByGenres_GenresId(genresId, pageable)
                .map(productMapper::toProductResponse);
    }

    public ProductResponse getProductById(Long id){
        return productMapper.toProductResponse(productReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND)));
    }

    public ProductDetailResponse getProductDetailById(Long id) {
        Product product = productReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
        return productMapper.toProductDetailResponse(product);
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
