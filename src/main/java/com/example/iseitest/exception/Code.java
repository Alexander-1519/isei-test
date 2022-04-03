package com.example.iseitest.exception;

public enum Code {

    AUTHENTICATION_FAILED(100),
    VALIDATION_FAILED(101),
    VERIFICATION_FAILED(102),
    USER_NOT_FOUND(110),
    USER_VALIDATION_FAILED(111),
    USER_ALREADY_EXISTS(112),
    USER_PASSWORD_NOT_VALID(113),
    USER_PASSWORD_ATTEMPT_LIMIT_EXCEEDED(114),

    USER_ROLE_NOT_FOUND(120),

    PASSWORD_NOT_MATCH(130),

    USER_REPORT_NOT_FOUND(140),

    UNEXPECTED(500),
    SYSTEM_ERROR(501),
    CONVERTING_FILE_ERROR(502);

    private final Integer codeIntValue;

    Code(Integer code) {
        this.codeIntValue = code;
    }

    public Integer getIntValue() {
        return codeIntValue;
    }
}
