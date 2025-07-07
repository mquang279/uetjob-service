package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.CompanyReviewDTO;
import com.example.demo.entity.Company;
import com.example.demo.entity.CompanyReview;
import com.example.demo.entity.User;
import com.example.demo.exception.CompanyReviewNotFoundException;
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
    public CompanyReview createReview(Long id, CompanyReviewDTO request) {
        String userEmail = SecurityService.getCurrentUserEmailLogin();
        User user = this.userService.getUserByEmail(userEmail);
        Company company = this.companyService.getCompanyById(id);
        CompanyReview review = new CompanyReview(request.getTitle(), request.getDescription(), request.getRating(),
                user, company);
        return this.companyReviewRepository.save(review);
    }

    @Override
    public CompanyReview updateReview(Long companyId, Long reviewId, CompanyReviewDTO reviewDTO) {
        CompanyReview review = this.getReviewById(reviewId);
        validateReviewBelongToCompany(companyId, review);
        validateReviewBelongToUser(review);
        if (reviewDTO.getDescription() != null) {
            review.setDescription(reviewDTO.getDescription());
        }
        if (reviewDTO.getTitle() != null) {
            review.setTitle(reviewDTO.getTitle());
        }
        if (reviewDTO.getRating() != null) {
            review.setRating(reviewDTO.getRating());
        }
        return this.companyReviewRepository.save(review);
    }

    @Override
    public CompanyReview getReviewById(Long id) {
        Optional<CompanyReview> review = this.companyReviewRepository.findById(id);
        return review.orElseThrow(() -> new CompanyReviewNotFoundException(id));
    }

    @Override
    public void validateReviewBelongToUser(CompanyReview review) {
        String userEmail = review.getUser().getEmail();
        if (!userEmail.equals(SecurityService.getCurrentUserEmailLogin())) {
            throw new IllegalArgumentException("Review with id " + review.getId() + " does not belong to this user.");
        }
    }

    @Override
    public void validateReviewBelongToCompany(Long companyId, CompanyReview review) {
        if (review.getCompany().getId() != companyId) {
            throw new IllegalArgumentException(
                    "Review with id " + review.getId() + " does not belong to this company.");
        }
    }

    @Override
    public void deleteReview(Long companyId, Long reviewId) {
        CompanyReview review = this.getReviewById(reviewId);
        validateReviewBelongToUser(review);
        validateReviewBelongToCompany(companyId, review);
        this.companyReviewRepository.delete(review);
    }

}
