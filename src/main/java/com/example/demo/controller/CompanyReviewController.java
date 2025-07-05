package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CompanyReview;
import com.example.demo.service.CompanyReviewService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/company-reviews")
public class CompanyReviewController {
    private final CompanyReviewService companyReviewService;

    public CompanyReviewController(CompanyReviewService companyReviewService) {
        this.companyReviewService = companyReviewService;
    }

    @PostMapping("")
    public ResponseEntity<CompanyReview> createReview(@RequestBody CompanyReview review) {
        CompanyReview data = this.companyReviewService.createReview(review);
        return ResponseEntity.ok().body(data);
    }

}
