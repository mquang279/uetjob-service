package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.CompanyReviewDTO;
import com.example.demo.entity.CompanyReview;

public interface CompanyReviewService {
    List<CompanyReview> getReviewsByCompanyId(Long id);

    List<CompanyReview> getReviewsByUserId(Long id);

    CompanyReview createReview(Long id, CompanyReviewDTO reviewDTO);

    CompanyReview updateReview(Long companyId, Long reviewId, CompanyReviewDTO reviewDTO);

    CompanyReview getReviewById(Long id);

    void validateReviewBelongToUser(CompanyReview review);

    void validateReviewBelongToCompany(Long companyId, CompanyReview review);
}
