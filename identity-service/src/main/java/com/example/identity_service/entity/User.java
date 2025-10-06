package com.example.identity_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    String username;
    String password;
    String fullname;
    String gender;
    String gmail;
    String phone;
    LocalDateTime createAt;
    LocalDate dob;

    //    @ElementCollection
    //    @CollectionTable(
    //            name = "user_roles", //thêm bảng trung gian,
    //            joinColumns = @JoinColumn(name = "user_id") // khóa ngoại với bảng user
    //    )
    //    @Column(name = "role")
    //    Set<String> roles;
    @ManyToMany
    Set<Role> roles;

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
    }
}
