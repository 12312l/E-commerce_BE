package com.example.identity_service.controller;

import com.example.identity_service.dto.request.AddressRequest;
import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.response.AddressResponse;
import com.example.identity_service.service.AddressService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressController {
    AddressService addressService;

    @PostMapping
    ApiResponse<AddressResponse> createAddress(@RequestBody  AddressRequest addressRequest){
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.createAddress(addressRequest))
                .build();
    }

    @GetMapping("/myAddress")
    ApiResponse<List<AddressResponse>> myAddress() {
        return ApiResponse.<List<AddressResponse>>builder()
                .result(addressService.getMyAddress())
                .build();
    }

    @PutMapping("/{addressId}")
    ApiResponse<AddressResponse> updateMyAddressById(@PathVariable("addressId") Long id, @RequestBody AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.updateMyAddressByID(id, request))
                .build();
    }

    @DeleteMapping("/{addressId}")
    String deleteAddressByUser(@PathVariable("addressId") Long addressId){
        addressService.deleteMyAddressById(addressId);
        return "Delete successfull";
    }

    @GetMapping("/{addressId}")
    ApiResponse<AddressResponse> getAddressById(@PathVariable("addressId") Long id){
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.getAddressById(id))
                .build();
    }
}
