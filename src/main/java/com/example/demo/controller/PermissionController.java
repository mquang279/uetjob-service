package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Permission;
import com.example.demo.service.PermissionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.createPermission(permission));
    }

    @GetMapping("/permissions")
    public ResponseEntity<PaginationResponse<Permission>> getAllPermissions(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(this.permissionService.getAllPermissions(page, pageSize));
    }

    @GetMapping("/permissions/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.permissionService.getPermissionById(id));
    }

    @PutMapping("/permissions/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission permission) {
        return ResponseEntity.ok().body(this.permissionService.updatePermission(id, permission));
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        this.permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}
