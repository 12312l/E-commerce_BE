package com.example.identity_service.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String fullname;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(
            regexp = "^(0[1-9])[0-9]{8,9}$",
            message = "Số điện thoại không hợp lệ! (Phải bắt đầu bằng 0 và có 10-11 số)")
    String phone;

    String gender;
    LocalDate dob;

    List<String> roles;
}
