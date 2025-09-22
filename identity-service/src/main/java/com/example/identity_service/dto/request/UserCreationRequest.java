package com.example.identity_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min =3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    @Size(min =2, max = 50, message = "Tên tối thiểu từ 2 đến 50 ký tự")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Tên chỉ được chứa chữ cái và khoảng trắng")
    String fullname;

    @NotBlank(message = "Vui lòng chọn giớ tính!")
    String gender;

    @NotBlank(message = "Email không được để trống")
    @Pattern(
            regexp = "^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$",
            message = "Email không hợp lệ"
    )
    String gmail;
    @NotNull(message = "Vui lòng chọn ngày sinh!")
    LocalDate dob;
}
