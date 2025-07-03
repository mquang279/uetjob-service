package com.example.demo.service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Company;

public interface CompanyService {
    Company createCompany(Company company);

    PaginationResponse<Company> getAllCompany(int page, int pageSize);

    Company updateCompany(Long id, Company company);

    Company getCompanyById(Long id);

    void deleteUserById(Long id);
}
