package com.example.demo.exception;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Job not found");
    }

    public JobNotFoundException(Long id) {
        super("Job with id " + id + " not found");
    }

    public JobNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
