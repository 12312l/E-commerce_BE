package com.example.identity_service.repository;

import com.example.identity_service.entity.FavouriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteProductRepository extends JpaRepository<FavouriteProduct, Long> {
    List<FavouriteProduct> findAllByUser_UserId(Long userId);
}
