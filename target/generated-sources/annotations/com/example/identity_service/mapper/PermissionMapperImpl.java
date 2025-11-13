package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.PermissionRequest;
import com.example.identity_service.dto.response.PermissionResponse;
import com.example.identity_service.entity.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T10:54:24+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toPermisstion(PermissionRequest request) {
        if ( request == null ) {
            return null;
        }

        Permission permission = new Permission();

        permission.setName( request.getName() );
        permission.setDescription( request.getDescription() );

        return permission;
    }

    @Override
    public PermissionResponse toPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.name( permission.getName() );
        permissionResponse.description( permission.getDescription() );

        return permissionResponse.build();
    }

    @Override
    public void updatePermission(Permission permission, PermissionRequest permissionRequest) {
        if ( permissionRequest == null ) {
            return;
        }

        permission.setName( permissionRequest.getName() );
        permission.setDescription( permissionRequest.getDescription() );
    }
}
