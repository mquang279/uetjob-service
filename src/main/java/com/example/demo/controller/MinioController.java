package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.PresignedUrlRequest;
import com.example.demo.dto.response.PresignedUrlResponse;
import com.example.demo.service.MinioStorageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class MinioController {

    private final MinioStorageService minioStorageService;

    public MinioController(MinioStorageService minioStorageService) {
        this.minioStorageService = minioStorageService;
    }

    @PostMapping("/minio/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(@Valid @RequestBody PresignedUrlRequest request) {
        String presignedUrl = minioStorageService.getPresignedUrlToPutObject(request.getFolder(),
                request.getFileName());
        String objectKey = request.getFolder() + "/" + request.getFileName();

        PresignedUrlResponse response = new PresignedUrlResponse(presignedUrl, objectKey);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
