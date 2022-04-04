package com.example.iseitest.service;

import com.example.iseitest.entity.Company;
import com.example.iseitest.exception.Code;
import com.example.iseitest.exception.ExceptionBuilder;
import com.example.iseitest.exception.IseiException;
import com.example.iseitest.exception.NoSuchCompanyException;
import com.example.iseitest.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) {
        Boolean existsByName = companyRepository.existsByName(company.getName());

        if (existsByName) {
            throw ExceptionBuilder.builder(Code.COMPANY_EXCEPTION)
                    .withMessage("Company already exists with name = " + company.getName())
                    .build(IseiException.class);
        }

        return companyRepository.save(company);
    }

    public Company getCompanyByName(String name) {
        return companyRepository.findByName(name).orElseThrow(() -> new NoSuchCompanyException(name));
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new NoSuchCompanyException(id));
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }
}
