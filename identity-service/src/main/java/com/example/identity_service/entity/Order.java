package com.example.identity_service.entity;

import com.example.identity_service.enums.DeliveryMethod;
import com.example.identity_service.enums.OrderStatus;
import com.example.identity_service.enums.PaymentMethod;
import com.example.identity_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "`order`") // ✅ dùng backtick để escape từ khóa
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    @Column(unique = true, nullable = false)
    String orderCode;

    Double totalAmount;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    DeliveryMethod deliveryMethod;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    Double shippingFee;

    LocalDateTime createAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderDetail> orderDetails = new ArrayList<>();


    @ManyToOne(optional = false)
    @JoinColumn(name = ("userId") , nullable = false)
    User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = ("addressId"), nullable = false)
    Address address;

    @PrePersist
    public void generateOrderCode() {
        this.createAt = LocalDateTime.now();

        if (this.orderCode == null) {
            String datePart = LocalDate.now().toString().replaceAll("-", ""); // 20251030
            String randomPart = UUID.randomUUID().toString().substring(0, 6).toUpperCase(); // 6 ký tự
            this.orderCode = "ORD-" + datePart + "-" + randomPart;
        }
    }
}
