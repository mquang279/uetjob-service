package com.example.demo.service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Permission;

public interface PermissionService {
    Permission createPermission(Permission permission);

    PaginationResponse<Permission> getAllPermissions(int page, int pageSize);

    Permission getPermissionById(Long id);

    Permission updatePermission(Long id, Permission permission);

    void deletePermission(Long id);
}
