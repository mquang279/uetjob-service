package com.example.demo.service.impl;

import com.example.demo.config.MinioConfig;
import com.example.demo.exception.StorageException;
import com.example.demo.service.MinioStorageService;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MinioStorageServiceImpl implements MinioStorageService {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    public MinioStorageServiceImpl(MinioClient minioClient, MinioConfig minioConfig) {
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

    @Override
    public void initializeBucket() {
        try {
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .build());

            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(minioConfig.getBucketName())
                                .build());
                System.out.println("Bucket " + minioConfig.getBucketName() + " created successfully!");
            } else {
                System.out.println("Bucket " + minioConfig.getBucketName() + " already existed!");
            }

            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket("uetjob")
                            .object("images.png")
                            .expiry(2, TimeUnit.HOURS)
                            .build());
            System.out.println("Download URL: " + url);

        } catch (Exception e) {
            System.out.println("Errow when creating bucket" + minioConfig.getBucketName());
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public String getPresignedUrlToPutObject(String folder, String fileName) {
        try {
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket("uetjob")
                            .object(folder + "/" + fileName)
                            .expiry(1, TimeUnit.DAYS)
                            .build());
            return url;
        } catch (Exception e) {
            throw new StorageException(e.getMessage());
        }
    }
}
