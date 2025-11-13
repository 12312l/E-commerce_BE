package com.example.identity_service.repository;

import com.example.identity_service.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
