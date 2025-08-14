package com.example.demo.entity;

import java.time.Instant;
import java.util.List;

import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.entity.enums.Gender;
import com.example.demo.service.SecurityService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    @Column(length = 1000, unique = true)
    private String refreshToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant createdAt;
    private Instant updatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CompanyReview> reviews;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Resume> resumes;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {

    }

    public User(RegistrationRequest userDTO) {
        this.setEmail(userDTO.getEmail());
        this.setUsername(userDTO.getUsername());
        this.setPassword(userDTO.getPassword());
        this.setRole(userDTO.getRole());
    }

    public User(String username, String email, String password, Integer age, Gender gender, String address,
            String refreshToken, Instant createdAt, Instant updatedAt, String createdBy, String updatedBy) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    @PrePersist
    public void handleBeforeCreate() {
        this.setCreatedAt(Instant.now());
        this.setCreatedBy(SecurityService.getCurrentUserEmailLogin());
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.setUpdatedAt(Instant.now());
        this.setUpdatedBy(SecurityService.getCurrentUserEmailLogin());
    }

}
