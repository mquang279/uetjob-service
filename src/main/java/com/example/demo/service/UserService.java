package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.User;

public interface UserService {

    User createUser(User user);

    List<User> getAllUser();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User getUserByEmail(String email);

    LoginDTO userToLoginDTO(User user);
}
