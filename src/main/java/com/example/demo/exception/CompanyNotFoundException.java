package com.example.demo.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super("Email already exists");
    }

    public CompanyNotFoundException(Long id) {
        super("Company with id " + id + " not found");
    }

    public CompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
