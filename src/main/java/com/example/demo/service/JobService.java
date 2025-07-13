package com.example.demo.service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Job;

public interface JobService {
    PaginationResponse<Job> getAllJobs(int page, int pageSize);

    PaginationResponse<Job> getAllJobsOfCompany(int page, int pageSize, Long companyId);

    Job getJobById(Long id);

    Job updateJob(Long companyId, Long jobId, Job job);

    void deleteJob(Long companyId, Long jobId);

    Job createJob(Long companyId, Job job);
}
