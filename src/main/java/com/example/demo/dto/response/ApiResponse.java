package com.example.demo.dto.response;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant timestamp;

    public ApiResponse() {
        this.timestamp = Instant.now();
    }

    public ApiResponse(HttpStatus httpStatus, String message, T data) {
        this.status = httpStatus.is2xxSuccessful() ? "success" : "error";
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }
}
