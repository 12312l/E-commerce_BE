package com.example.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.mapping.Join;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderDetailId;

    Integer quantity;

    String size;

    String color;

    Double price; // ✅ thêm nếu bạn muốn lưu giá tại thời điểm mua (tránh thay đổi theo giá gốc)

    @ManyToOne(optional = false)
    @JoinColumn(name = "variantId", nullable = false)
    ProductVariant productVariant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orderId", nullable = false)
    Order order;
}
