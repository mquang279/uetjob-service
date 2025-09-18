package com.example.demo.dto.response;

import java.time.Instant;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.Gender;

import lombok.Data;

@Data
public class RegistrationResponse {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private Gender gender;
    private String address;
    private Role role;
    private Instant createdAt;

    public RegistrationResponse(Long id, String username, String email, Integer age, Gender gender, String address,
            Instant createdAt, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.createdAt = createdAt;
        this.role = role;
    }

    public RegistrationResponse(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setAge(user.getAge());
        this.setGender(user.getGender());
        this.setAddress(user.getAddress());
        this.setCreatedAt(user.getCreatedAt());
        this.setRole(user.getRole());
    }
}
