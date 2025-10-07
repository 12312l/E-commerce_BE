package com.example.identity_service.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.identity_service.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
//    String password;
    @Size(min = 2, max = 50, message = "INVALID_FULLNAME")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Tên chỉ được chứa chữ cái và khoảng trắng")
    String fullname;


    String phone;

    String gender;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;

//    List<String> roles;
}
