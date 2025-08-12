package com.example.demo.dto.response;

import java.time.Instant;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResponse {
    private int status;

    private String message;

    private String fileName;

    private Instant uploadedAt;

    public UploadResponse(int status, String message, String fileName) {
        this.status = status;
        this.message = message;
        this.fileName = fileName;
    }

    @PrePersist
    public void handleBeforeCreate() {
        this.uploadedAt = Instant.now();
    }
}
