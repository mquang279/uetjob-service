package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.CreateCompanyReviewRequest;
import com.example.demo.entity.Company;
import com.example.demo.entity.CompanyReview;
import com.example.demo.entity.User;
import com.example.demo.repository.CompanyReviewRepository;
import com.example.demo.service.CompanyReviewService;
import com.example.demo.service.CompanyService;
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;

@Service
public class CompanyReviewServiceImpl implements CompanyReviewService {
    private final CompanyReviewRepository companyReviewRepository;
    private final UserService userService;
    private final CompanyService companyService;

    public CompanyReviewServiceImpl(CompanyReviewRepository companyReviewRepository, UserService userService,
            CompanyService companyService) {
        this.companyReviewRepository = companyReviewRepository;
        this.userService = userService;
        this.companyService = companyService;
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

    @Override
    public CompanyReview createReview(Long id, CreateCompanyReviewRequest request) {
        String userEmail = SecurityService.getCurrentUserEmailLogin();
        User user = this.userService.getUserByEmail(userEmail);
        Company company = this.companyService.getCompanyById(id);
        CompanyReview review = new CompanyReview(request.getTitle(), request.getDescription(), request.getRating(),
                user, company);
        return this.companyReviewRepository.save(review);
    }

}
