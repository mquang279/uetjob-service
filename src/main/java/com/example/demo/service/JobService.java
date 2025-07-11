package com.example.demo.service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Job;

public interface JobService {
    PaginationResponse<Job> getAllJobs(int page, int pageSize);

    Job getJobById(Long id);

    Job updateJob(Long id, Job job);

    void deleteJob(Long id);
}
