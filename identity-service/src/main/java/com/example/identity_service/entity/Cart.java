package com.example.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cartId;

    String size;

    Integer quantity;

    Double totalPrice;

    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "variantId", nullable = false)
    ProductVariant productVariant;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    User user;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
