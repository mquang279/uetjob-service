package com.example.demo.exception;

public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException() {
        super("Permission not found");
    }

    public PermissionNotFoundException(Long id) {
        super("Permission with id " + id + " not found");
    }

    public PermissionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
