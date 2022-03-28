package com.example.iseitest.exception;

import org.springframework.http.HttpStatus;

public class NoSuchEntityException extends IseiException {

    public NoSuchEntityException() {
        super(Code.UNEXPECTED);
    }

    public NoSuchEntityException(String message) {
        super(message, Code.UNEXPECTED);
    }

    public NoSuchEntityException(String message, Code code) {
        super(message, code);
    }

    public NoSuchEntityException(String message, Code code, Throwable cause) {
        super(message, code, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
