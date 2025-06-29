package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.LoginDTO;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.service.SecurityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityService securityService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityService securityService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginDTO loginDTO) {
        // Generate authenticationToken from input username and password
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // Validate authenticationToken
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // If authenticationToken is valid, create JWT Token
        String token = this.securityService.createToken(authentication);
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK, "Login", token);
        return ResponseEntity.ok().body(response);
    }
}
