package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationDTO {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Confirm password cannot be blank")
    private String password;

    @NotBlank(message = "Confirm password cannot be blank")
    private String confirmPassword;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
