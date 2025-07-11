package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Job;
import com.example.demo.exception.JobNotFoundException;
import com.example.demo.repository.JobRepository;
import com.example.demo.service.CompanyService;
import com.example.demo.service.JobService;

public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public PaginationResponse<Job> getAllJobs(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Job> jobs = this.jobRepository.findAll(pageable);
        PaginationResponse<Job> response = new PaginationResponse<>(jobs);
        return response;
    }

    @Override
    public Job getJobById(Long id) {
        Optional<Job> jobOptional = this.jobRepository.findById(id);
        return jobOptional.orElseThrow(() -> new JobNotFoundException(id));
    }

    @Override
    public Job updateJob(Long id, Job job) {
        Job existingJob = this.getJobById(id);

        if (job.getTitle() != null) {
            existingJob.setTitle(job.getTitle());
        }
        if (job.getDescription() != null) {
            existingJob.setDescription(job.getDescription());
        }
        if (job.getLocation() != null) {
            existingJob.setLocation(job.getLocation());
        }
        if (job.getStartDate() != null) {
            existingJob.setStartDate(job.getStartDate());
        }
        if (job.getEndDate() != null) {
            existingJob.setEndDate(job.getEndDate());
        }
        if (job.getMinSalary() != null) {
            existingJob.setMinSalary(job.getMinSalary());
        }
        if (job.getMaxSalary() != null) {
            existingJob.setMaxSalary(job.getMaxSalary());
        }
        if (job.getQuantity() != null) {
            existingJob.setQuantity(job.getQuantity());
        }
        if (job.getLevel() != null) {
            existingJob.setLevel(job.getLevel());
        }
        if (job.getActive() != null) {
            existingJob.setActive(job.getActive());
        }
        if (job.getSkills() != null) {
            existingJob.setSkills(job.getSkills());
        }

        return this.jobRepository.save(existingJob);
    }

    @Override
    public void deleteJob(Long id) {
        Job job = this.getJobById(id);
        this.jobRepository.delete(job);
    }
}
