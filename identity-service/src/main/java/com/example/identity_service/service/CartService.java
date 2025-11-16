package com.example.identity_service.service;

import com.example.identity_service.dto.request.CartRequest;
import com.example.identity_service.dto.response.CartResponse;
import com.example.identity_service.dto.response.ProductVariantResponse;
import com.example.identity_service.entity.Cart;
import com.example.identity_service.entity.ProductVariant;
import com.example.identity_service.entity.User;
import com.example.identity_service.enums.CartAction;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.CartMapper;
import com.example.identity_service.repository.CartRepository;
import com.example.identity_service.repository.ProductVariantRepository;
import com.example.identity_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    CartMapper cartMapper;

    UserRepository userRepository;

    ProductVariantRepository productVariantRepository;

    @Transactional
    public CartResponse addCart(CartRequest cartRequest){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        ProductVariant variant = productVariantRepository.findById(cartRequest.getVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        // Kiểm tra cart đã tồn tại chưa
        Optional<Cart> existingCart = cartRepository
                .findByUser_UserIdAndProductVariant_VariantIdAndSize(user.getUserId(), variant.getVariantId(), cartRequest.getSize());

        Cart cart;
        if (existingCart.isPresent()) {
            // Nếu cart đã tồn tại, cộng dồn số lượng
            cart = existingCart.get();
            int newQuantity = cart.getQuantity() + cartRequest.getQuantity();
            cart.setQuantity(newQuantity);
            cart.setTotalPrice(variant.getProduct().getPrice() * newQuantity);
        } else {
            // Nếu chưa tồn tại, tạo cart mới
            cart = cartMapper.toCart(cartRequest);
            cart.setUser(user);
            cart.setProductVariant(variant);
            // totalPrice và quantity đã được set trong @AfterMapping
            cartMapper.calculatePrice(cartRequest, cart, variant);
        }

        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    public List<CartResponse> myCart(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        return cartRepository.findByUser_UserId(user.getUserId())
                .stream()
                .map(cartMapper::toCartResponse)
                .toList();
    }



    @Transactional
    public CartResponse updateCart(Long carId, String action){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        Cart cart = cartRepository.findByCartIdAndUser_UserId(carId, user.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXSISTED));

        int quantity = cart.getQuantity();

        CartAction cartAction = CartAction.valueOf(action.toUpperCase());

        switch (cartAction){
            case INCREASE:
                quantity +=1;
                break;
            case DECREASE:
                quantity-=1;
                break;
        }

        if(quantity <=0){
            cartRepository.delete(cart);
            return null;
        }

        cart.setQuantity(quantity);
        cart.setTotalPrice(cart.getProductVariant().getProduct().getPrice()* quantity);
        return cartMapper.toCartResponse(cartRepository.save(cart));
    }
}
