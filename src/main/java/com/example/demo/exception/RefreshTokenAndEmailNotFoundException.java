package com.example.demo.exception;

public class RefreshTokenAndEmailNotFoundException extends RuntimeException {
    public RefreshTokenAndEmailNotFoundException() {
        super("User with given Refresh Token and Email does not exist");
    }

    public RefreshTokenAndEmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
