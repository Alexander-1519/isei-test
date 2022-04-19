package com.example.iseitest.exception;

public class NoSuchCompanyTagException extends NoSuchEntityException {

    public NoSuchCompanyTagException(Long id) {
        super(String.format("Company tag not found: %s", id), Code.COMPANY_TAG_NOT_FOUND);
    }
}
