package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.dto.response.RegistrationResponse;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

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
        public ResponseEntity<RegistrationResponse> createUser(
                        @Valid @RequestBody RegistrationRequest user) {
                String hashPassword = this.passwordEncoder.encode(user.getPassword());
                user.setPassword(hashPassword);
                RegistrationResponse data = this.userService.createUser(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(data);
        }

        @GetMapping("")
        public ResponseEntity<PaginationResponse<UserDTO>> getAllUser(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "15") int pageSize) {
                PaginationResponse<UserDTO> data = this.userService.getAllUser(page, pageSize);
                return ResponseEntity.ok().body(data);
        }

        @GetMapping("/{id}")
        public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
                UserDTO userDTO = this.userService.convertToUserDTO(this.userService.getUserById(id));
                return ResponseEntity.ok().body(userDTO);
        }

        @PutMapping("/{id}")
        public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User user) {
                if (user.getPassword() != null) {
                        String hashPassword = this.passwordEncoder.encode(user.getPassword());
                        user.setPassword(hashPassword);
                }
                UserDTO userDTO = this.userService.convertToUserDTO(this.userService.updateUser(id, user));
                return ResponseEntity.ok().body(userDTO);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
                this.userService.deleteUser(id);
                return ResponseEntity.noContent().build();
        }
}
