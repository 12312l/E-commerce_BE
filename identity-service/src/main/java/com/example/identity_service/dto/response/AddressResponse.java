package com.example.identity_service.dto.response;

import com.example.identity_service.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse {
    Long addressId;

    String fullname;
    String phone;
    String province;
    String district;
    String village;
    String detailAddress;
    boolean typeAddress;
    Long userId;
}
