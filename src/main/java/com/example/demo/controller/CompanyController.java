package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Company;
import com.example.demo.entity.CompanyReview;
import com.example.demo.service.CompanyReviewService;
import com.example.demo.service.CompanyService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyReviewService companyReviewService;

    public CompanyController(CompanyService companyService, CompanyReviewService companyReviewService) {
        this.companyService = companyService;
        this.companyReviewService = companyReviewService;
    }

    @GetMapping("")
    public ResponseEntity<PaginationResponse<Company>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int pageSize) {
        PaginationResponse<Company> data = this.companyService.getAllCompany(page, pageSize);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        Company data = this.companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company data = this.companyService.getCompanyById(id);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        Company data = this.companyService.updateCompany(id, company);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        this.companyService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    

}
