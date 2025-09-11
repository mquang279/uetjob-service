package com.example.demo.service;

public interface MinioStorageService {
    void initializeBucket();

    String getPresignedUrlToPutObject(String folder, String fileName);
}
