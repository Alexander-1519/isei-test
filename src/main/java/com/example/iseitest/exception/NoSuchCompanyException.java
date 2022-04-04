package com.example.iseitest.exception;

public class NoSuchCompanyException extends NoSuchEntityException {

    public NoSuchCompanyException(Long id) {
        super(String.format("Company not found: %s", id), Code.COMPANY_NOT_FOUND);
    }

    public NoSuchCompanyException(String name) {
        super(String.format("Company not found: %s", name), Code.COMPANY_NOT_FOUND);
    }
}
