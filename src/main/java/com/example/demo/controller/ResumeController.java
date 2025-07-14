package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.StorageService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class ResumeController {
    private final StorageService storageService;

    public ResumeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/resumes")
    public void uploadResume(@RequestBody MultipartFile file) {
        this.storageService.store(file);
    }

}
