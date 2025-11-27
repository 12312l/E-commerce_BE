package com.example.identity_service.service;

import com.example.identity_service.dto.response.ProductResponse;
import com.example.identity_service.entity.FavouriteProduct;
import com.example.identity_service.entity.Product;
import com.example.identity_service.entity.User;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.ProductMapper;
import com.example.identity_service.repository.FavouriteProductRepository;
import com.example.identity_service.repository.ProductReponsitory;
import com.example.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FavouriteProductService {
    FavouriteProductRepository favouriteProductRepository;
    ProductReponsitory productReponsitory;
    ProductMapper productMapper;
    UserRepository userRepository;
    
    public String addFavouriteProduct(Long productId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        Product product = productReponsitory.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

        FavouriteProduct favouriteProduct=new FavouriteProduct();
        favouriteProduct.setProduct(product);
        favouriteProduct.setUser(user);
        
        favouriteProductRepository.save(favouriteProduct);
        return "Đã thêm sản phẩm vào danh sách yêu thích";
    }

    public List<ProductResponse> getAllFavouriteOfUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        List<FavouriteProduct> favouriteProducts = favouriteProductRepository.findAllByUser_UserId(user.getUserId());

        List<Product> products = new ArrayList<>();
        for(FavouriteProduct item: favouriteProducts ){
            products.add(productReponsitory.findById(item.getProduct().getProductId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND)));
        }

        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public String deleteFavourite(Long favouriteId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        FavouriteProduct favouriteProduct = favouriteProductRepository.findById(favouriteId).orElseThrow(() -> new AppException(ErrorCode.FAVOURITE_NOT_EXSISTED));

        favouriteProductRepository.delete(favouriteProduct);

        return "Đã xóa sản phẩm yêu thích thành công";
    }
    
}
