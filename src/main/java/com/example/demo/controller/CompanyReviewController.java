package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.CreateCompanyReviewRequest;
import com.example.demo.entity.CompanyReview;
import com.example.demo.service.CompanyReviewService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")
public class CompanyReviewController {
    private final CompanyReviewService companyReviewService;

    public CompanyReviewController(CompanyReviewService companyReviewService) {
        this.companyReviewService = companyReviewService;
    }

    @PostMapping("/companies/{id}/reviews")
    public ResponseEntity<CompanyReview> createReview(@PathVariable Long id,
            @RequestBody CreateCompanyReviewRequest request) {
        CompanyReview data = this.companyReviewService.createReview(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @GetMapping("companies/{id}/reviews")
    public ResponseEntity<List<CompanyReview>> getAllCompanyReviews(@PathVariable Long id) {
        List<CompanyReview> reviews = this.companyReviewService.getReviewsByCompanyId(id);
        return ResponseEntity.ok().body(reviews);
    }

    @GetMapping("users/{id}/review")
    public ResponseEntity<List<CompanyReview>> getAllUserReviews(@PathVariable Long id) {
        List<CompanyReview> reviews = this.companyReviewService.getReviewsByUserId(id);
        return ResponseEntity.ok().body(reviews);
    }
}
