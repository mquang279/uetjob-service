package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CompanyReview;
import com.example.demo.repository.CompanyReviewRepository;
import com.example.demo.service.CompanyReviewService;

@Service
public class CompanyReviewServiceImpl implements CompanyReviewService {
    private final CompanyReviewRepository companyReviewRepository;

    public CompanyReviewServiceImpl(CompanyReviewRepository companyReviewRepository) {
        this.companyReviewRepository = companyReviewRepository;
    }

    @Override
    public List<CompanyReview> getReviewsByCompanyId(Long id) {
        List<CompanyReview> reviews = this.companyReviewRepository.findByCompanyId(id);
        return reviews;
    }

    @Override
    public List<CompanyReview> getReviewsByUserId(Long userId) {
        List<CompanyReview> reviews = this.companyReviewRepository.findByUserId(userId);
        return reviews;
    }

    // TODO: - Check if user have already review this company
    @Override
    public CompanyReview createReview(CompanyReview review) {
        return this.companyReviewRepository.save(review);
    }

}
