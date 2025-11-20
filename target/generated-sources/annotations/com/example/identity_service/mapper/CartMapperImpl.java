package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.CartRequest;
import com.example.identity_service.dto.response.CartResponse;
import com.example.identity_service.entity.Cart;
import com.example.identity_service.entity.Color;
import com.example.identity_service.entity.Product;
import com.example.identity_service.entity.ProductVariant;
import com.example.identity_service.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T21:09:27+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public Cart toCart(CartRequest cartRequest) {
        if ( cartRequest == null ) {
            return null;
        }

        Cart.CartBuilder cart = Cart.builder();

        cart.size( cartRequest.getSize() );
        cart.quantity( cartRequest.getQuantity() );

        return cart.build();
    }

    @Override
    public CartResponse toCartResponse(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartResponse.CartResponseBuilder cartResponse = CartResponse.builder();

        cartResponse.productId( cartProductVariantProductProductId( cart ) );
        cartResponse.productName( cartProductVariantProductName( cart ) );
        Double price = cartProductVariantProductPrice( cart );
        if ( price != null ) {
            cartResponse.productPrice( String.valueOf( price ) );
        }
        cartResponse.colorName( cartProductVariantColorName( cart ) );
        cartResponse.variantId( cartProductVariantVariantId( cart ) );
        cartResponse.userId( cartUserUserId( cart ) );
        cartResponse.cartId( cart.getCartId() );
        cartResponse.size( cart.getSize() );
        cartResponse.quantity( cart.getQuantity() );
        cartResponse.totalPrice( cart.getTotalPrice() );

        cartResponse.urlImage( cart.getProductVariant().getImages() != null && !cart.getProductVariant().getImages().isEmpty() ? cart.getProductVariant().getImages().get(0).getUrl() : null );

        return cartResponse.build();
    }

    private Long cartProductVariantProductProductId(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        ProductVariant productVariant = cart.getProductVariant();
        if ( productVariant == null ) {
            return null;
        }
        Product product = productVariant.getProduct();
        if ( product == null ) {
            return null;
        }
        Long productId = product.getProductId();
        if ( productId == null ) {
            return null;
        }
        return productId;
    }

    private String cartProductVariantProductName(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        ProductVariant productVariant = cart.getProductVariant();
        if ( productVariant == null ) {
            return null;
        }
        Product product = productVariant.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Double cartProductVariantProductPrice(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        ProductVariant productVariant = cart.getProductVariant();
        if ( productVariant == null ) {
            return null;
        }
        Product product = productVariant.getProduct();
        if ( product == null ) {
            return null;
        }
        Double price = product.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }

    private String cartProductVariantColorName(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        ProductVariant productVariant = cart.getProductVariant();
        if ( productVariant == null ) {
            return null;
        }
        Color color = productVariant.getColor();
        if ( color == null ) {
            return null;
        }
        String name = color.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long cartProductVariantVariantId(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        ProductVariant productVariant = cart.getProductVariant();
        if ( productVariant == null ) {
            return null;
        }
        Long variantId = productVariant.getVariantId();
        if ( variantId == null ) {
            return null;
        }
        return variantId;
    }

    private Long cartUserUserId(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        User user = cart.getUser();
        if ( user == null ) {
            return null;
        }
        Long userId = user.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }
}
