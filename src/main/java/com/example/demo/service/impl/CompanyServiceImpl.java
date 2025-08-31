package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.entity.Company;
import com.example.demo.exception.CompanyNotFoundException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {
        return this.companyRepository.save(company);
    }

    @Override
    public PaginationResponse<Company> getAllCompany(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Company> companies = this.companyRepository.findAll(pageable);
        PaginationResponse<Company> response = new PaginationResponse<>(companies);
        return response;
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Company existingCompany = getCompanyById(id);
        if (company.getName() != null) {
            existingCompany.setName(company.getName());
        }
        if (company.getEmail() != null) {
            existingCompany.setEmail(company.getEmail());
        }
        if (company.getAddress() != null) {
            existingCompany.setAddress(company.getAddress());
        }
        if (company.getDescription() != null) {
            existingCompany.setDescription(company.getDescription());
        }
        if (company.getLogo() != null) {
            existingCompany.setLogo(company.getLogo());
        }
        return this.companyRepository.save(existingCompany);
    }

    @Override
    public Company getCompanyById(Long id) {
        Optional<Company> companyOptional = this.companyRepository.findById(id);
        return companyOptional.orElseThrow(() -> new CompanyNotFoundException(id));
    }

    @Override
    public void deleteUserById(Long id) {
        Company company = this.getCompanyById(id);
        this.companyRepository.delete(company);
    }

    @Override
    public Long getTotalCompanies() {
        return this.companyRepository.count();
    }

}
