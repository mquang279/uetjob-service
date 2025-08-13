package com.example.demo.exception;

public class PermissionAlreadyExistsException extends RuntimeException {
    public PermissionAlreadyExistsException() {
        super("Permission has already exists");
    }

    public PermissionAlreadyExistsException(String apiPath, String method) {
        super("Permission with api " + method + apiPath + " has already exists");
    }

    public PermissionAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
