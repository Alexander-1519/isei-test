package com.example.iseitest.service;

import com.example.iseitest.entity.Company;
import com.example.iseitest.entity.CompanyTag;
import com.example.iseitest.exception.NoSuchCompanyException;
import com.example.iseitest.exception.NoSuchCompanyTagException;
import com.example.iseitest.repository.CompanyRepository;
import com.example.iseitest.repository.CompanyTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyTagService {

    private final CompanyTagRepository companyTagRepository;
    private final CompanyRepository companyRepository;

    public CompanyTagService(CompanyTagRepository companyTagRepository, CompanyRepository companyRepository) {
        this.companyTagRepository = companyTagRepository;
        this.companyRepository = companyRepository;
    }

    public CompanyTag createTag(String tagName) {
        CompanyTag companyTag = new CompanyTag();
        companyTag.setName(tagName);

        return companyTagRepository.save(companyTag);
    }

    public void deleteTag(Long id) {
        CompanyTag tag = companyTagRepository.findById(id).orElseThrow(() -> new NoSuchCompanyTagException(id));

        companyTagRepository.delete(tag);
    }

    public CompanyTag getTagById(Long id) {
        return companyTagRepository.findById(id).orElseThrow(() -> new NoSuchCompanyTagException(id));
    }

    public List<CompanyTag> getAll() {
        return companyTagRepository.findAll();
    }
}
