package com.example.iseitest.exception;

public class IseiValidationException extends IseiException{

    public IseiValidationException() {
        super(Code.VALIDATION_FAILED);
    }

    public IseiValidationException(String message) {
        super(message, Code.VALIDATION_FAILED);
    }

    public IseiValidationException(String message, Throwable cause) {
        super(message, Code.VALIDATION_FAILED, cause);
    }
}
