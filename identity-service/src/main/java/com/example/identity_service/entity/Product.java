package com.example.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;

    String name;

    String material;

    String description;

    String instruction;

    Double price;

    Integer discountPercent;

    LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "genresId", nullable = false)
    Genres genres;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    Order order;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductVariant> variants = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
    }
}
