package com.example.iseitest.service;

import com.example.iseitest.entity.Company;
import com.example.iseitest.entity.CompanyTag;
import com.example.iseitest.exception.*;
import com.example.iseitest.repository.CompanyRepository;
import com.example.iseitest.repository.CompanyTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyTagRepository tagRepository;

    public CompanyService(CompanyRepository companyRepository, CompanyTagRepository tagRepository) {
        this.companyRepository = companyRepository;
        this.tagRepository = tagRepository;
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

    public Company addTagToCompany(Long tagId, Long companyId) {
        CompanyTag companyTag = tagRepository.findById(tagId).orElseThrow(() -> new NoSuchCompanyTagException(tagId));

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new NoSuchCompanyException(companyId));

        company.addTag(companyTag);

        return companyRepository.save(company);
    }
}
