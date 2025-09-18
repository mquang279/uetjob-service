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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.RegistrationResponse;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final SecurityService securityService;
        private final UserService userService;
        private final PasswordEncoder passwordEncoder;

        @Value("${refresh.token.expiration.time}")
        private long refreshTokenExpiration;

        public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
                        SecurityService securityService,
                        UserService userService, PasswordEncoder passwordEncoder) {
                this.authenticationManagerBuilder = authenticationManagerBuilder;
                this.securityService = securityService;
                this.userService = userService;
                this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginDTO) {
                // Generate authenticationToken from input username and password
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                loginDTO.getUsername(), loginDTO.getPassword());

                // Validate authenticationToken
                Authentication authentication = authenticationManagerBuilder.getObject()
                                .authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Format response
                LoginResponse data = new LoginResponse();
                String email = loginDTO.getUsername();
                UserDTO userDTO = this.userService.convertToUserDTO(this.userService.getUserByEmail(email));
                data.setUser(userDTO);
                // Create JWT Access Token
                String accessToken = this.securityService.createAccessToken(email, userDTO.getRole().getName(),
                                userDTO);
                data.setAccessToken(accessToken);

                // Create JWT Refresh Token and store to database
                String refreshToken = this.securityService.createRefreshToken(email, userDTO);
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
                                .body(data);
        }

        @GetMapping("/account")
        public ResponseEntity<LoginResponse> getUserInformation() {
                try {
                        String email = SecurityService.getCurrentUserEmailLogin();

                        // Check if email is null or empty (user not authenticated)
                        if (email == null || email.trim().isEmpty() || "anonymousUser".equals(email)) {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                        }

                        User user = this.userService.getUserByEmail(email);
                        UserDTO userDTO = this.userService.convertToUserDTO(user);

                        // Create response with user info and access token
                        LoginResponse data = new LoginResponse();
                        data.setUser(userDTO);

                        // Create new access token
                        String accessToken = this.securityService.createAccessToken(email, userDTO.getRole().getName(),
                                        userDTO);
                        data.setAccessToken(accessToken);

                        return ResponseEntity.ok().body(data);
                } catch (Exception e) {
                        // Handle any authentication-related exceptions
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
        }

        @GetMapping("/refresh")
        public ResponseEntity<LoginResponse> getAccessToken(
                        @CookieValue(name = "refresh_token") String refreshToken) {
                // Check if refresh token is valid or not, if not valid throw an exception
                Jwt decodedJwt = this.securityService.checkValidRefreshToken(refreshToken);

                // Get user by refresh token and email
                String email = decodedJwt.getSubject();
                User user = this.userService.getUserByRefreshTokenAndEmail(refreshToken, email);
                LoginResponse data = new LoginResponse();
                UserDTO userDTO = this.userService.convertToUserDTO(user);
                data.setUser(userDTO);
                // Create JWT Access Token
                String accessToken = this.securityService.createAccessToken(email, userDTO.getRole().getName(),
                                userDTO);
                data.setAccessToken(accessToken);

                // Create new JWT Refresh Token and Store to database
                String newRefreshToken = this.securityService.createRefreshToken(email, userDTO);
                this.userService.updateUserRefreshToken(userDTO.getId(), newRefreshToken);

                // Create cookies contain new refresh token
                ResponseCookie cookies = ResponseCookie
                                .from("refresh_token", newRefreshToken)
                                .path("/")
                                .maxAge(refreshTokenExpiration)
                                .secure(true)
                                .httpOnly(true)
                                .build();

                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, cookies.toString())
                                .body(data);
        }

        @PostMapping("/register")
        public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest user) {
                String hashPassword = this.passwordEncoder.encode(user.getPassword());
                user.setPassword(hashPassword);
                RegistrationResponse data = this.userService.createUser(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(data);
        }

        @PostMapping("/logout")
        public ResponseEntity<Void> logout(@CookieValue(name = "refresh_token") String refreshToken) {
                // Clear refresh token of user in database
                String email = SecurityService.getCurrentUserEmailLogin();
                this.userService.clearRefreshToken(email);

                // Clear cookies
                ResponseCookie cookies = ResponseCookie
                                .from("refresh_token", null)
                                .path("/")
                                .maxAge(0)
                                .secure(true)
                                .httpOnly(true)
                                .build();

                return ResponseEntity.noContent()
                                .header(HttpHeaders.SET_COOKIE, cookies.toString())
                                .build();
        }

}
