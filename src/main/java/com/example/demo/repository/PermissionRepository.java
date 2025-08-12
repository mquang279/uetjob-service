package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
