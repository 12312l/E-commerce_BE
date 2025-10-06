package com.example.identity_service.dto.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long userId;
    String username;
    String fullname;
    LocalDate dob;
    String gmail;
    String phone;
    String gender;
    Set<RoleResponse> roles;
}
