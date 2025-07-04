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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.LoginResponse;
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

        private final UserController userController;
        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final SecurityService securityService;
        private final UserService userService;

        @Value("${refresh.token.expiration.time}")
        private long refreshTokenExpiration;

        public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
                        SecurityService securityService,
                        UserService userService,
                        UserController userController) {
                this.authenticationManagerBuilder = authenticationManagerBuilder;
                this.securityService = securityService;
                this.userService = userService;
                this.userController = userController;
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginDTO) {
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
                String accessToken = this.securityService.createAccessToken(email, userDTO);
                data.setAccessToken(accessToken);
                ApiResponse<LoginResponse> response = new ApiResponse<>(HttpStatus.OK, "User login", data);

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
                                .body(response);
        }

        @GetMapping("/account")
        public ResponseEntity<ApiResponse<UserDTO>> getUserInformation() {
                String email = SecurityService.getCurrentUserEmailLogin();
                User user = this.userService.getUserByEmail(email);
                ApiResponse<UserDTO> response = new ApiResponse<>(HttpStatus.OK, "Get user information",
                                this.userService.convertToUserDTO(user));
                return ResponseEntity.ok().body(response);
        }

        @GetMapping("/refresh")
        public ResponseEntity<ApiResponse<LoginResponse>> getAccessToken(
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
                String accessToken = this.securityService.createAccessToken(email, userDTO);
                data.setAccessToken(accessToken);
                ApiResponse<LoginResponse> response = new ApiResponse<>(HttpStatus.OK, "User login", data);

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
                                .body(response);
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
