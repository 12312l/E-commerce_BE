package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.AddressRequest;
import com.example.identity_service.dto.response.AddressResponse;
import com.example.identity_service.entity.Address;
import com.example.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mappings({
            @Mapping(target = "addressId", ignore = true), // bỏ qua ID vì sẽ tự sinh
            @Mapping(target = "user", expression = "java(mapUserIdToUser(addressRequest.getUserId()))")
    })
    Address toAddress(AddressRequest addressRequest);

    @Mapping(target = "userId", source = "user.userId")
    AddressResponse toAddressResponse(Address address);

    void updateAddress(@MappingTarget Address address, AddressRequest request);

//     🟢 Hàm hỗ trợ tạo đối tượng User từ userId (để tránh lỗi null khi map)
    default User mapUserIdToUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setUserId(userId);
        return user;
    }
}
