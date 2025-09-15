package com.example.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;

    @ElementCollection
    @CollectionTable(
            name = "user_roles", //thêm bảng trung gian,
            joinColumns = @JoinColumn(name = "user_id") // khóa ngoại với bảng user
    )
    @Column(name = "role")
    Set<String> roles;
}
