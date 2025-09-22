package com.example.identity_service.service;

import com.example.identity_service.dto.request.PermissionRequest;
import com.example.identity_service.dto.response.PermissionResponse;
import com.example.identity_service.entity.Permission;
import com.example.identity_service.mapper.PermissionMapper;
import com.example.identity_service.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermisstion(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermission() {
        var permissions = permissionRepository.findAll();
        return permissions
                        .stream()
                        .map(permissionMapper::toPermissionResponse).toList();
    }

    public PermissionResponse updatePermission(String name, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(name)
                .orElseThrow(() -> new RuntimeException("NOT FOUND PERMISSION"));

        permissionMapper.updatePermission(permission, permissionRequest);

        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public void deletePermission(String name) {
        permissionRepository.deleteById(name);
    }
}
