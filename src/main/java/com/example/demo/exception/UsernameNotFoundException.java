package com.example.demo.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException() {
        super("User ID is not valid");
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }

    public UsernameNotFoundException(Long id) {
        super("User ID is not valid: " + id);
    }

    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
