package com.example.demo.service;

import com.example.demo.dto.response.CreateResumeResponse;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.dto.response.UpdateResumeResponse;
import com.example.demo.entity.Resume;

public interface ResumeService {
    CreateResumeResponse creatResume(Resume resume);

    PaginationResponse<Resume> getAllResume(int page, int pageSize);

    Resume getResumeById(Long id);

    void deleteResumeById(Long id);

    UpdateResumeResponse updateResume(Long id, Resume resume);
}
