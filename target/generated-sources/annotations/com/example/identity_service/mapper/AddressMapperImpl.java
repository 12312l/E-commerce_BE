package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.AddressRequest;
import com.example.identity_service.dto.response.AddressResponse;
import com.example.identity_service.entity.Address;
import com.example.identity_service.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T22:36:34+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address toAddress(AddressRequest addressRequest) {
        if ( addressRequest == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.fullname( addressRequest.getFullname() );
        address.phone( addressRequest.getPhone() );
        address.province( addressRequest.getProvince() );
        address.district( addressRequest.getDistrict() );
        address.village( addressRequest.getVillage() );
        address.detailAddress( addressRequest.getDetailAddress() );
        address.typeAddress( addressRequest.isTypeAddress() );

        address.user( mapUserIdToUser(addressRequest.getUserId()) );

        return address.build();
    }

    @Override
    public AddressResponse toAddressResponse(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressResponse.AddressResponseBuilder addressResponse = AddressResponse.builder();

        addressResponse.userId( addressUserUserId( address ) );
        addressResponse.addressId( address.getAddressId() );
        addressResponse.fullname( address.getFullname() );
        addressResponse.phone( address.getPhone() );
        addressResponse.province( address.getProvince() );
        addressResponse.district( address.getDistrict() );
        addressResponse.village( address.getVillage() );
        addressResponse.detailAddress( address.getDetailAddress() );
        addressResponse.typeAddress( address.isTypeAddress() );

        return addressResponse.build();
    }

    @Override
    public void updateAddress(Address address, AddressRequest request) {
        if ( request == null ) {
            return;
        }

        address.setFullname( request.getFullname() );
        address.setPhone( request.getPhone() );
        address.setProvince( request.getProvince() );
        address.setDistrict( request.getDistrict() );
        address.setVillage( request.getVillage() );
        address.setDetailAddress( request.getDetailAddress() );
        address.setTypeAddress( request.isTypeAddress() );
    }

    private Long addressUserUserId(Address address) {
        if ( address == null ) {
            return null;
        }
        User user = address.getUser();
        if ( user == null ) {
            return null;
        }
        Long userId = user.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }
}
