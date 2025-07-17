package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.CreateResumeResponse;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.dto.response.UpdateResumeResponse;
import com.example.demo.entity.Resume;
import com.example.demo.service.ResumeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/resumes")
    public ResponseEntity<CreateResumeResponse> createResume(@Valid @RequestBody Resume resume) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.resumeService.creatResume(resume));
    }

    @GetMapping("/resumes")
    public ResponseEntity<PaginationResponse<Resume>> getAllResumes(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(this.resumeService.getAllResume(page, pageSize));
    }

    @GetMapping("/resumes/{id}")
    public ResponseEntity<Resume> getResumeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.resumeService.getResumeById(id));
    }

    @PutMapping("/resumes/{id}")
    public ResponseEntity<UpdateResumeResponse> updateResume(@PathVariable Long id, @RequestBody Resume resume) {
        return ResponseEntity.ok().body(this.resumeService.updateResume(id, resume));
    }

    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        this.resumeService.deleteResumeById(id);
        return ResponseEntity.noContent().build();
    }

}
