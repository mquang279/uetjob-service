package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.entity.Company;
import com.example.demo.service.CompanyService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Company>>> getAllCompanies() {
        ApiResponse<List<Company>> response = new ApiResponse<List<Company>>(HttpStatus.OK, "Get all companies",
                this.companyService.getAllCompany());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Company>> createCompany(@Valid @RequestBody Company company) {
        ApiResponse<Company> response = new ApiResponse<>(HttpStatus.OK, "Create company",
                this.companyService.createCompany(company));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> getCompanyById(@PathVariable Long id) {
        ApiResponse<Company> response = new ApiResponse<>(HttpStatus.OK, "Get company with id " + id,
                this.companyService.getCompanyById(id));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        ApiResponse<Company> response = new ApiResponse<>(HttpStatus.OK, "Update company with id " + id,
                this.companyService.updateCompany(id, company));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        this.companyService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
