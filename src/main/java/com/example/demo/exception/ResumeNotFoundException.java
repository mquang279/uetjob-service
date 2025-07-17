package com.example.demo.exception;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException() {
        super("Resume not found");
    }

    public ResumeNotFoundException(Long id) {
        super("Resume with id " + id + " not found");
    }

    public ResumeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
