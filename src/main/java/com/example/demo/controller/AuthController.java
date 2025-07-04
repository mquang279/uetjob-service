package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityService securityService;
    private final UserService userService;

    @Value("${refresh.token.expiration.time}")
    private long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityService securityService,
            UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityService = securityService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginDTO) {
        // Generate authenticationToken from input username and password
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // Validate authenticationToken
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // If authenticationToken is valid, create JWT Access Token
        String accessToken = this.securityService.createAccessToken(authentication);

        // Format response
        LoginResponse data = new LoginResponse();
        data.setAccessToken(accessToken);
        String email = loginDTO.getUsername();
        UserDTO userDTO = this.userService.convertToUserDTO(this.userService.getUserByEmail(email));
        data.setUser(userDTO);
        ApiResponse<LoginResponse> response = new ApiResponse<>(HttpStatus.OK, "User login", data);

        // Create JWT Refresh Token and store to database
        String refreshToken = this.securityService.createRefreshToken(email, data);
        System.out.println(refreshToken);
        this.userService.updateUserRefreshToken(userDTO.getId(), refreshToken);

        // Create Cookies
        ResponseCookie cookies = ResponseCookie
                .from("refresh_token", refreshToken)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .secure(true)
                .httpOnly(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookies.toString())
                .body(response);
    }
}
