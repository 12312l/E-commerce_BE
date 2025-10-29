package com.example.identity_service.repository;

import com.example.identity_service.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenresRepository extends JpaRepository<Genres, Long> {

    List<Genres> findAllByCategory_CategoryId(Long categoryId);
}
