package com.example.iseitest.controller;

import com.example.iseitest.dto.company.CompanyOutputDto;
import com.example.iseitest.entity.Company;
import com.example.iseitest.mapper.CompanyMapper;
import com.example.iseitest.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdCompany);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(company);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyOutputDto>> getAllCompanies(@RequestParam(required = false) List<String> tags) {
        List<Company> companies = companyService.getAll(tags);

        return ResponseEntity.status(HttpStatus.OK)
                .body(companyMapper.toListOutputDto(companies));
    }

    @GetMapping("/companies/names")
    public ResponseEntity<List<CompanyOutputDto>> getAllByName(
            @RequestParam(required = false, defaultValue = "") String name) {
        List<Company> companies = companyService.getAllByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .body(companyMapper.toListOutputDto(companies));
    }

    @PutMapping("/companies/{companyId}/tags/{tagId}")
    public ResponseEntity<Company> addTagToCompany(@PathVariable Long companyId, @PathVariable Long tagId) {
        Company company = companyService.addTagToCompany(tagId, companyId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(company);
    }
}
