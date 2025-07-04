package com.example.demo.dto.response;

import lombok.Data;

@Data
public class UserClaims {
    private Long id;
    private String username;
    private String email;

    public UserClaims(UserDTO userDTO) {
        this.setId(userDTO.getId());
        this.setUsername(userDTO.getUsername());
        this.setEmail(userDTO.getEmail());
    }
}
