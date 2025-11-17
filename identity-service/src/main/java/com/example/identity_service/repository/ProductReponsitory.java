package com.example.identity_service.repository;

import com.example.identity_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReponsitory extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByCreateAtDesc();

    @Query(value = "SELECT * FROM product WHERE name LIKE %:keyword%", nativeQuery = true)
    List<Product> searchProducts(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM product WHERE product_id IN (:productIds) AND price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    List<Product> filterByPrice(
            @Param("productIds") List<Long> productIds,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    List<Product> findAllByGenres_GenresId(Long id);
}
