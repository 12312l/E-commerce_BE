package com.example.identity_service.repository;

import com.example.identity_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReponsitory extends JpaRepository<Product, Long> {

}
