package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.CreateResumeResponse;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.dto.response.UpdateResumeResponse;
import com.example.demo.entity.Resume;
import com.example.demo.exception.ResumeNotFoundException;
import com.example.demo.exception.ResumeNotValidException;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.service.JobService;
import com.example.demo.service.ResumeService;
import com.example.demo.service.UserService;

@Service
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserService userService;
    private final JobService jobService;

    public ResumeServiceImpl(ResumeRepository resumeRepository, UserService userService, JobService jobService) {
        this.resumeRepository = resumeRepository;
        this.userService = userService;
        this.jobService = jobService;
    }

    @Override
    public CreateResumeResponse creatResume(Resume resume) {
        validateUserIdAndJobId(resume);
        this.resumeRepository.save(resume);
        CreateResumeResponse response = new CreateResumeResponse(resume.getId(), resume.getUrl(), resume.getCreatedAt(),
                resume.getCreatedBy());
        return response;
    }

    public void validateUserIdAndJobId(Resume resume) {
        if (resume.getJob() == null || resume.getUser() == null) {
            throw new ResumeNotValidException();
        }
        this.userService.getUserById(resume.getUser().getId());
        this.jobService.getJobById(resume.getJob().getId());
    }

    @Override
    public PaginationResponse<Resume> getAllResume(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Resume> resumes = this.resumeRepository.findAll(pageable);
        PaginationResponse<Resume> response = new PaginationResponse<>(resumes);
        return response;
    }

    @Override
    public Resume getResumeById(Long id) {
        Optional<Resume> resumeOptional = this.resumeRepository.findById(id);
        return resumeOptional.orElseThrow(() -> new ResumeNotFoundException(id));
    }

    @Override
    public void deleteResumeById(Long id) {
        Resume resume = this.getResumeById(id);
        this.resumeRepository.delete(resume);
    }

    @Override
    public UpdateResumeResponse updateResume(Long id, Resume resume) {
        Resume existingResume = this.getResumeById(id);
        existingResume.setStatus(resume.getStatus());
        this.resumeRepository.save(existingResume);
        UpdateResumeResponse response = new UpdateResumeResponse(existingResume.getId(), existingResume.getUrl(),
                existingResume.getStatus(), existingResume.getUpdatedAt(), existingResume.getUpdatedBy());
        return response;
    }
}
