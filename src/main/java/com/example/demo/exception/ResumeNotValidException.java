package com.example.demo.exception;

public class ResumeNotValidException extends RuntimeException {
    public ResumeNotValidException() {
        super("Resume is not valid");
    }
}
