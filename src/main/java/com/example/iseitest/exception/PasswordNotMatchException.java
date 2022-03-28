package com.example.iseitest.exception;

public class PasswordNotMatchException extends IseiException {

    public PasswordNotMatchException(String password) {
        super(String.format("Password is not correct: %s", password), Code.PASSWORD_NOT_MATCH);
    }
}
