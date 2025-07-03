package com.example.demo.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant timestamp;

    public ErrorResponse() {
        this.timestamp = Instant.now();
    }

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
    }

}
