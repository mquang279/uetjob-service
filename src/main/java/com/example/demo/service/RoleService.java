package com.example.demo.service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Role;

public interface RoleService {
    PaginationResponse<Role> getAllRoles(int page, int pageSize);

    Role createRole(Role role);

    Role getRoleById(Long id);

    void deleteRole(Long id);

    Role updateRole(Long id, Role role);
}
