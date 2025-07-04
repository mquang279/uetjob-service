package com.example.demo.dto.request;

import com.example.demo.entity.enums.Gender;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private Integer age;
    private Gender gender;
    private String address;
}
