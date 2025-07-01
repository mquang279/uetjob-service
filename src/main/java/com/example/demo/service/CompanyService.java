package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Company;

public interface CompanyService {
    Company createCompany(Company company);

    List<Company> getAllCompany();

    Company updateCompany(Long id, Company company);

    Company getCompanyById(Long id);

    void deleteUserById(Long id);
}
