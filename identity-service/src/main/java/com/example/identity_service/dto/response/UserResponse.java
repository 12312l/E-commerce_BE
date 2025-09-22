package com.example.identity_service.dto.response;

import com.example.identity_service.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


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
