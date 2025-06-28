package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UserIdNotValidException;
import com.example.demo.exception.UsernameNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        // Check if email already exists
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElseThrow(() -> new UserIdNotValidException(id));
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
    public LoginDTO userToLoginDTO(User user) {
        return new LoginDTO(user.getUsername(), user.getPassword());
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

}
