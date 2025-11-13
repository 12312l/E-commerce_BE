package com.example.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long imageId;

    String filePath;

    String url;

    String name;

    String type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "variantId", nullable = false)
    ProductVariant productVariant;
}
