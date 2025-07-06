package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.CreateCompanyReviewRequest;
import com.example.demo.entity.CompanyReview;

public interface CompanyReviewService {
    List<CompanyReview> getReviewsByCompanyId(Long id);

    List<CompanyReview> getReviewsByUserId(Long id);

    CompanyReview createReview(Long id, CreateCompanyReviewRequest request);
}
