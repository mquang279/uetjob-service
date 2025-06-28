package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exception.UserIdNotValidException;
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
            existingUser.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            existingUser.setName(user.getName());
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

}
