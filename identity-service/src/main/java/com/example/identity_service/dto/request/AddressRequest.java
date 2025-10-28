package com.example.identity_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequest {

    @Size(min = 2, max = 50, message = "INVALID_FULLNAME")
    String fullname;

    @Pattern(regexp = "^(0[0-9]{9}|\\+84[0-9]{9})$", message = "INVALID_PHONE")
    String phone;

    @NotBlank(message = "PROVINCE_REQUIRED")
    String province;

    @NotBlank(message = "DISTRICT_REQUIRED")
    String district;

    @NotBlank(message = "VILLAGE_REQUIRED")
    String village;

    @Size(min = 3, max = 255, message = "INVALID_DETAIL_ADDRESS")
    String detailAddress;

    boolean typeAddress;

    @NotNull(message = "USER_ID_REQUIRED")
    Long userId;
//    @NotNull(message = "Please enter username")
//    String username;
}
