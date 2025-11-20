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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    CartMapper cartMapper;

    UserRepository userRepository;

    ProductVariantRepository productVariantRepository;

    RedisTemplate<String, Object> redisTemplate;
    String KEY_PREFIX = "guest:cart:";

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

    private String buildKey(String guestId) {
        return KEY_PREFIX + guestId;
    }

    private void saveCart(String guestId, List<CartRequest> items) {
        redisTemplate.opsForValue().set(buildKey(guestId), items, Duration.ofDays(7));
    }

    public List<CartRequest> getGuestCart(String guestId) {
        Object data = redisTemplate.opsForValue().get(buildKey(guestId));
        if (data == null) return new ArrayList<>();
        return (List<CartRequest>) data;
    }

    public List<CartRequest> addGuestCart(String guestId, CartRequest request) {
        List<CartRequest> cart = getGuestCart(guestId);

        Optional<CartRequest> exist = cart.stream()
                .filter(i -> i.getVariantId().equals(request.getVariantId())
                        && i.getSize().equals(request.getSize()))
                .findFirst();

        if (exist.isPresent()) {
            exist.get().setQuantity(exist.get().getQuantity() + request.getQuantity());
        } else {
            cart.add(request);
        }

        saveCart(guestId, cart);
        return cart;
    }

    public List<CartResponse> convertGuestCart(String guestId) {
        List<CartRequest> items = getGuestCart(guestId);
        Map<String, CartResponse> mergedMap = new HashMap<>();

        for (CartRequest item : items) {
            ProductVariant variant = productVariantRepository.findById(item.getVariantId())
                    .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

            double price = variant.getProduct().getPrice();

            // KEY định danh 1 hàng trong giỏ: variantId + size
            String key = item.getVariantId() + "_" + item.getSize();

            if (mergedMap.containsKey(key)) {
                // Nếu tồn tại → cộng dồn số lượng
                CartResponse existing = mergedMap.get(key);

                int newQuantity = existing.getQuantity() + item.getQuantity();
                existing.setQuantity(newQuantity);
                existing.setTotalPrice(price * newQuantity);

            } else {
                // Nếu chưa có → tạo mới
                CartResponse response = CartResponse.builder()
                        .cartId(null)
                        .userId(null)
                        .variantId(item.getVariantId())
                        .size(item.getSize())
                        .quantity(item.getQuantity())
                        .totalPrice(price * item.getQuantity())

                        .productId(variant.getProduct().getProductId())
                        .productName(variant.getProduct().getName())
                        .productPrice(String.valueOf(price))
                        .colorName(variant.getColor().getName())

                        .urlImage(
                                !variant.getImages().isEmpty()
                                        ? variant.getImages().get(0).getUrl()
                                        : null
                        )

                        .build();

                mergedMap.put(key, response);
            }
        }

        // Trả về list sau khi merge xong
        return new ArrayList<>(mergedMap.values());
    }





}
