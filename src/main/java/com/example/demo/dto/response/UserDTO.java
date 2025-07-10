package com.example.demo.dto.response;

import java.time.Instant;

import com.example.demo.entity.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private Gender gender;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant updatedAt;
}
