package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.CartRequest;
import com.example.identity_service.dto.response.CartResponse;
import com.example.identity_service.entity.Cart;
import com.example.identity_service.entity.Image;
import com.example.identity_service.entity.ProductVariant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "cartId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "productVariant", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    Cart toCart(CartRequest cartRequest);

    @AfterMapping
    default void calculatePrice(CartRequest req,
                                @MappingTarget Cart cart,
                                ProductVariant variant) {
        cart.setTotalPrice(variant.getProduct().getPrice() * req.getQuantity());
    }

    @Mapping(target = "productId", source = "productVariant.product.productId")
    @Mapping(target = "productName", source = "productVariant.product.name")
    @Mapping(target = "productPrice", source = "productVariant.product.price")
    @Mapping(target = "colorName", source = "productVariant.color.name")
    @Mapping(target = "variantId", source = "productVariant.variantId")
//    @Mapping(source = "productVariant.images", target = "urlImage", qualifiedByName = "mapFirstImage")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "urlImage",
            expression = "java(cart.getProductVariant().getImages() != null && !cart.getProductVariant().getImages().isEmpty() ? cart.getProductVariant().getImages().get(0).getUrl() : null)")
    CartResponse toCartResponse(Cart cart);
//    CartResponse toCartResponse(Cart cart);

    // Method để lấy ảnh đầu tiên
    @Named("mapFirstImage")
    default String mapFirstImage(List<Image> images) {
        if (images != null && !images.isEmpty()) {
            return images.get(0).getUrl();
        }
        return null;
    }
}
