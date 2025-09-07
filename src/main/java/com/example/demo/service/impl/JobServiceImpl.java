package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Company;
import com.example.demo.entity.Job;
import com.example.demo.exception.JobNotFoundException;
import com.example.demo.repository.JobRepository;
import com.example.demo.service.CompanyService;
import com.example.demo.service.JobService;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final CompanyService companyService;

    public JobServiceImpl(JobRepository jobRepository, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.companyService = companyService;
    }

    @Override
    public PaginationResponse<Job> getAllJobs(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Job> jobs = this.jobRepository.findAll(pageable);
        PaginationResponse<Job> response = new PaginationResponse<>(jobs);
        return response;
    }

    @Override
    public PaginationResponse<Job> getAllJobsOfCompany(int page, int pageSize, Long companyId) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Job> jobs = this.jobRepository.findByCompany(companyId, pageable);
        PaginationResponse<Job> response = new PaginationResponse<>(jobs);
        return response;
    }

    @Override
    public Job getJobById(Long id) {
        Optional<Job> jobOptional = this.jobRepository.findById(id);
        return jobOptional.orElseThrow(() -> new JobNotFoundException(id));
    }

    @Override
    public Job updateJob(Long companyId, Long jobId, Job job) {
        Job existingJob = this.getJobById(jobId);

        validateJobBelongToCompany(existingJob, companyId);

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
    public void deleteJob(Long companyId, Long jobId) {
        Job job = this.getJobById(jobId);
        validateJobBelongToCompany(job, companyId);
        this.jobRepository.delete(job);
    }

    @Override
    public Job createJob(Long companyId, Job job) {
        Company company = this.companyService.getCompanyById(companyId);

        job.setCompany(company);

        if (job.getActive() == null) {
            job.setActive(true);
        }

        return this.jobRepository.save(job);
    }

    public void validateJobBelongToCompany(Job job, Long companyId) {
        if (job.getCompany().getId() != companyId) {
            throw new IllegalArgumentException("This job does not belong to this company.");
        }
    }

    @Override
    public Long getTotalJobs() {
        return this.jobRepository.count();
    }

    @Override
    public PaginationResponse<Job> getActiveJobs(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Job> jobs = this.jobRepository.findActiveJobs(pageable);
        PaginationResponse<Job> response = new PaginationResponse<>(jobs);
        return response;
    }

    @Override
    public Long getTotalActiveJobs() {
        return this.jobRepository.countActiveJobs();
    }

    @Override
    public PaginationResponse<Job> findJobByParam(int page, int pageSize, String param) {
        param = param.toLowerCase();
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Job> jobs = this.jobRepository.findJobByParam(pageable, param);
        PaginationResponse<Job> response = new PaginationResponse<>(jobs);
        return response;
    }
}
