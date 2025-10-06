package com.example.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.identity_service.dto.request.PermissionRequest;
import com.example.identity_service.dto.response.PermissionResponse;
import com.example.identity_service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermisstion(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    void updatePermission(@MappingTarget Permission permission, PermissionRequest permissionRequest);
}
