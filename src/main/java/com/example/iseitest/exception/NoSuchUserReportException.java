package com.example.iseitest.exception;

public class NoSuchUserReportException extends NoSuchEntityException {

    public NoSuchUserReportException(Long id) {
        super(String.format("UserReport not found: %s", id), Code.USER_REPORT_NOT_FOUND);
    }
}
