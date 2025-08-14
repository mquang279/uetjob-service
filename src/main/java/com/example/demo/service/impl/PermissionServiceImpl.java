package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Permission;
import com.example.demo.exception.PermissionAlreadyExistsException;
import com.example.demo.exception.PermissionNotFoundException;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
    private PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission createPermission(Permission permission) {
        if (this.permissionRepository.existsByApiPathAndMethod(permission.getApiPath(), permission.getMethod())) {
            throw new PermissionAlreadyExistsException(permission.getApiPath(), permission.getMethod());
        }
        return this.permissionRepository.save(permission);
    }

    @Override
    public PaginationResponse<Permission> getAllPermissions(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Permission> permissions = this.permissionRepository.findAll(pageable);
        PaginationResponse<Permission> response = new PaginationResponse<>(permissions);
        return response;
    }

    @Override
    public Permission getPermissionById(Long id) {
        Optional<Permission> permissionOptional = this.permissionRepository.findById(id);
        return permissionOptional.orElseThrow(() -> new PermissionNotFoundException(id));
    }

    @Override
    public Permission updatePermission(Long id, Permission permission) {
        Permission existingPermission = this.getPermissionById(id);

        if (permission.getApiPath() != null) {
            existingPermission.setApiPath(permission.getApiPath());
        }

        if (permission.getName() != null) {
            existingPermission.setName(permission.getName());
        }

        if (permission.getMethod() != null) {
            existingPermission.setMethod(permission.getMethod());
        }

        if (permission.getRoles() != null) {
            existingPermission.setRoles(permission.getRoles());
        }

        return this.permissionRepository.save(existingPermission);
    }

    @Override
    public void deletePermission(Long id) {
        Permission permission = this.getPermissionById(id);
        this.permissionRepository.delete(permission);
    }

}
