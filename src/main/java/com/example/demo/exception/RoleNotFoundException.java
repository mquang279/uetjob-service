package com.example.demo.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super("Role not found");
    }

    public RoleNotFoundException(Long id) {
        super("Role with id " + id + " not found");
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
