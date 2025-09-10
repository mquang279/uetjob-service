package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface MinioStorageService {
    void initializeBucket();
}
