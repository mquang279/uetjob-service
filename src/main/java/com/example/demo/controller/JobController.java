package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Job;
import com.example.demo.service.JobService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ResponseEntity<PaginationResponse<Job>> getAllJobs(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int pageSize) {
        return ResponseEntity.ok().body(this.jobService.getAllJobs(page, pageSize));
    }

    @GetMapping("/jobs/count")
    public ResponseEntity<Long> getTotalJobsCount() {
        return ResponseEntity.ok().body(this.jobService.getTotalJobs());
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.jobService.getJobById(id));
    }

    @GetMapping("/companies/{company_id}/jobs")
    public ResponseEntity<PaginationResponse<Job>> getAllJobsOfCompany(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize, @PathVariable("company_id") Long id) {
        return ResponseEntity.ok().body(this.jobService.getAllJobsOfCompany(page, pageSize, id));
    }

    @PostMapping("/companies/{company_id}/jobs")
    public ResponseEntity<Job> createJob(@PathVariable("company_id") Long companyId, @RequestBody Job job) {
        return ResponseEntity.ok().body(this.jobService.createJob(companyId, job));
    }

    @PutMapping("/companies/{company_id}/jobs/{job_id}")
    public ResponseEntity<Job> updateJob(@PathVariable("company_id") Long companyId, @PathVariable("job_id") Long jobId,
            @RequestBody Job job) {
        return ResponseEntity.ok().body(this.jobService.updateJob(companyId, jobId, job));
    }

    @DeleteMapping("/companies/{company_id}/job/{job_id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("company_id") Long companyId,
            @PathVariable("job_id") Long jobId) {
        this.jobService.deleteJob(companyId, jobId);
        return ResponseEntity.noContent().build();
    }
}
