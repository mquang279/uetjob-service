package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Role;
import com.example.demo.exception.RoleNotFoundException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public PaginationResponse<Role> getAllRoles(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Role> roles = this.roleRepository.findAll(pageable);
        PaginationResponse<Role> response = new PaginationResponse<>(roles);
        return response;
    }

    @Override
    public Role createRole(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = this.getRoleById(id);
        this.roleRepository.delete(role);
    }

    @Override
    public Role updateRole(Long id, Role role) {
        Role existingRole = this.getRoleById(id);

        if (role.getName() != null) {
            existingRole.setName(role.getName());
        }

        if (role.getUsers() != null) {
            existingRole.setUsers(role.getUsers());
        }

        return this.roleRepository.save(existingRole);
    }

    @Override
    public Role getRoleById(Long id) {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        return roleOptional.orElseThrow(() -> new RoleNotFoundException(id));
    }

}
