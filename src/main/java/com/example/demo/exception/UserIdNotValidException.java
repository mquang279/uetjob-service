package com.example.demo.exception;

public class UserIdNotValidException extends RuntimeException {
    public UserIdNotValidException() {
        super("User ID is not valid");
    }

    public UserIdNotValidException(String message) {
        super(message);
    }

    public UserIdNotValidException(Long id) {
        super("User ID is not valid: " + id);
    }

    public UserIdNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
