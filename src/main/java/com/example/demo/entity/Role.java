package com.example.demo.entity;

import java.time.Instant;
import java.util.List;

import com.example.demo.service.SecurityService;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private Long id;

    private String name;

    private Boolean active;

    private Instant createdAt;

    private String createdBy;

    private Instant updatedAt;

    private String updatedBy;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityService.getCurrentUserEmailLogin();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityService.getCurrentUserEmailLogin();
    }

    @ManyToMany
    @JoinTable(
        name="permissions_roles",
        joinColumns = @JoinColumn(name= "role_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Permission> permissions;
}
