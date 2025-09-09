package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.UploadResponse;
import com.example.demo.service.StorageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class StorageController {
    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/files")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder, @RequestParam("file_name") String fileName) {
        UploadResponse response = new UploadResponse(HttpStatus.OK.value(), "File uploaded successfully",
                this.storageService.store(file, folder, fileName));
        return ResponseEntity.ok().body(response);
    }
}
