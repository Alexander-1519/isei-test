package com.example.iseitest.controller;

import com.example.iseitest.entity.Company;
import com.example.iseitest.service.CompanyService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
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
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(companies);
    }
}
