package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.dto.response.RegistrationResponse;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegistrationResponse createUser(RegistrationRequest userDTO) {
        if (this.userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException(userDTO.getEmail());
        }
        User user = new User(userDTO);
        RegistrationResponse response = new RegistrationResponse(this.userRepository.save(user));
        return response;
    }

    @Override
    public PaginationResponse<UserDTO> getAllUser(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> users = this.userRepository.findAll(pageable);
        Page<UserDTO> userDTOs = users.map(this::convertToUserDTO);
        PaginationResponse<UserDTO> response = new PaginationResponse<>(userDTOs);
        return response;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = this.getUserById(id);

        if (user.getEmail() != null) {
            if (this.userRepository.existsByEmail(user.getEmail())) {
                User userWithEmail = this.userRepository.findByEmail(user.getEmail());
                if (userWithEmail != null && !userWithEmail.getId().equals(id)) {
                    throw new EmailAlreadyExistsException(user.getEmail());
                }
            }
            existingUser.setEmail(user.getEmail());
        }

        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }

        return this.userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = this.getUserById(id);
        this.userRepository.delete(user);
    }

    @Override
    public LoginRequest userToLoginDTO(User user) {
        return new LoginRequest(user.getUsername(), user.getPassword());
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setAddress(user.getAddress());
        userDTO.setAge(user.getAge());
        userDTO.setEmail(user.getEmail());
        userDTO.setGender(user.getGender());
        userDTO.setId(user.getId());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }

}
