package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.dto.response.RegistrationResponse;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
        private final UserService userService;
        private final PasswordEncoder passwordEncoder;

        public UserController(UserService userService, PasswordEncoder passwordEncoder) {
                this.userService = userService;
                this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("")
        public ResponseEntity<ApiResponse<RegistrationResponse>> createUser(@RequestBody RegistrationRequest user) {
                String hashPassword = this.passwordEncoder.encode(user.getPassword());
                user.setPassword(hashPassword);
                ApiResponse<RegistrationResponse> response = new ApiResponse<>(HttpStatus.CREATED, "Create user",
                                this.userService.createUser(user));
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(response);
        }

        @GetMapping("")
        public ResponseEntity<ApiResponse<PaginationResponse<UserDTO>>> getAllUser(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "15") int pageSize) {
                ApiResponse<PaginationResponse<UserDTO>> response = new ApiResponse<>(HttpStatus.OK, "Get all users",
                                this.userService.getAllUser(page, pageSize));
                return ResponseEntity.ok()
                                .body(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
                UserDTO userDTO = this.userService.convertToUserDTO(this.userService.getUserById(id));
                ApiResponse<UserDTO> response = new ApiResponse<>(HttpStatus.OK, "Get user with id " + id,
                                userDTO);
                return ResponseEntity.ok()
                                .body(response);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @RequestBody User user) {
                if (user.getPassword() != null) {
                        String hashPassword = this.passwordEncoder.encode(user.getPassword());
                        user.setPassword(hashPassword);
                }
                UserDTO userDTO = this.userService.convertToUserDTO(this.userService.updateUser(id, user));
                ApiResponse<UserDTO> response = new ApiResponse<>(HttpStatus.OK, "Update user with id " + id, userDTO);
                return ResponseEntity.ok()
                                .body(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
                this.userService.deleteUser(id);
                return ResponseEntity.noContent().build();
        }
}
