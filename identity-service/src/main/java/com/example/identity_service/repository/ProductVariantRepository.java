package com.example.identity_service.repository;

import com.example.identity_service.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findByProduct_ProductIdAndColor_ColorId(Long productId, Long colorId);
}
