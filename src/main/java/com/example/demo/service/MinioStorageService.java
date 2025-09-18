package com.example.demo.service;

import java.util.List;

public interface MinioStorageService {
    void initializeBucket();

    String getPresignedUrlToPutObject(String folder, String fileName);

    List<String> getAllFilesNameInFolder(String folder);
}
