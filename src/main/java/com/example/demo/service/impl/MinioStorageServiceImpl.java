package com.example.demo.service.impl;

import com.example.demo.config.MinioConfig;
import com.example.demo.exception.StorageException;
import com.example.demo.service.MinioStorageService;
import com.nimbusds.jose.shaded.gson.JsonObject;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
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
                            .bucket(minioConfig.getBucketName())
                            .object(folder + "/" + fileName)
                            .expiry(1, TimeUnit.DAYS)
                            .build());
            return url;
        } catch (Exception e) {
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public List<String> getAllFilesNameInFolder(String folder) {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(minioConfig.getBucketName()).prefix(folder + "/").build());
        List<String> filesName = new ArrayList<>();

        for (Result<Item> result : results) {
            try {
                Item item = result.get();
                filesName.add(item.objectName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filesName;
    }
}
