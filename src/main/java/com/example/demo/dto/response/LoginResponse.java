package com.example.demo.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private UserDTO user;
}
