package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.dto.response.RegistrationResponse;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.entity.User;

public interface UserService {

    RegistrationResponse createUser(RegistrationRequest userDTO);

    PaginationResponse<UserDTO> getAllUser(int page, int pageSize);

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User getUserByEmail(String email);

    LoginRequest userToLoginDTO(User user);

    UserDTO convertToUserDTO(User user);

    void updateUserRefreshToken(Long id, String refreshToken);
}
