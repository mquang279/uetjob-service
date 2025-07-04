package com.example.demo.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User does not exist");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        super("User with id " + id + " does not exist");
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
