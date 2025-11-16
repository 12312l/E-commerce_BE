package com.example.identity_service.repository;

import com.example.identity_service.dto.response.CartResponse;
import com.example.identity_service.entity.Cart;
import com.example.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser_UserId(Long userId);

    Optional<Cart> findByCartIdAndUser_UserId(Long cartId, Long userId);

    Optional<Cart> findByUser_UserIdAndProductVariant_VariantIdAndSize(Long userId, Long variantId, String Size);
}
