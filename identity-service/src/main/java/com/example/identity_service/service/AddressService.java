package com.example.identity_service.service;

import com.example.identity_service.dto.request.AddressRequest;
import com.example.identity_service.dto.response.AddressResponse;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.Address;
import com.example.identity_service.entity.User;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.AddressMapper;
import com.example.identity_service.repository.AddressRepository;
import com.example.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressService {
    AddressRepository addressRepository;

    AddressMapper addressMapper;

    UserRepository userRepository;

//    @PostAuthorize("returnObject.userId==authentication.principal.id")
public AddressResponse createAddress(AddressRequest addressRequest) {
    // Lấy user từ DB theo userId trong request
    User user = userRepository.findById(addressRequest.getUserId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));


    // Lấy username của người đang đăng nhập
    String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

    // Kiểm tra user hiện tại có đúng là người đang đăng nhập không
    if (!currentUsername.equals(user.getUsername())) {
        throw new AppException(ErrorCode.UNAUTHORIZED); // hoặc ErrorCode.FORBIDDEN
    }

    // Ánh xạ request sang entity
    Address address = addressMapper.toAddress(addressRequest);
    address.setUser(user); // Gán lại user thực tế từ DB

    // Lưu và trả về kết quả
    return addressMapper.toAddressResponse(addressRepository.save(address));
}


    @PreAuthorize("hasRole('ADMIN')")
    public List<AddressResponse> getAddress() {
        return  addressRepository.findAll().stream().map(addressMapper::toAddressResponse).collect(Collectors.toList());
    }

    // 🟢 Lấy danh sách địa chỉ theo userId (cho user xem địa chỉ của chính họ)
    public List<AddressResponse> getAddressesByUserId(Long userId) {
        return addressRepository.findByUser_UserId(userId)
                .stream()
                .map(addressMapper::toAddressResponse)
                .collect(Collectors.toList());
    }

    // 🟢 Lấy danh sách địa chỉ theo userId (cho user xem địa chỉ của chính họ)
    public List<AddressResponse> getMyAddress() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user =
                userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        return addressRepository.findByUser_UserId(user.getUserId())
                .stream()
                .map(addressMapper::toAddressResponse)
                .collect(Collectors.toList());
    }

    public AddressResponse updateMyAddressByID(Long id, AddressRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        Address address = addressRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOTFOUND));

        if(!address.getUser().getUserId().equals(user.getUserId())){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        addressMapper.updateAddress(address, request);

        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    public void deleteMyAddressById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXSISTED));

        Address address = addressRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOTFOUND));

        if(!address.getUser().getUserId().equals(user.getUserId())){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        addressRepository.deleteById(id);
    }

//    get address by id
    public AddressResponse getAddressById(Long id)
    {
        Address address = addressRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOTFOUND));
        return addressMapper.toAddressResponse(address);
    }
}
