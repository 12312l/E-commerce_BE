package com.example.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long addressId;

    String fullname;
    String phone;
    String province;
    String district;
    String village;
    String detailAddress;
    boolean typeAddress;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    User user;
}
