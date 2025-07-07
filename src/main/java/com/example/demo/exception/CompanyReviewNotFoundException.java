package com.example.demo.exception;

public class CompanyReviewNotFoundException extends RuntimeException {
    public CompanyReviewNotFoundException() {
        super("Review not found");
    }

    public CompanyReviewNotFoundException(Long id) {
        super("Review with id " + id + " not found");
    }

    public CompanyReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
