package com.example.iseitest.exception;

public class UserAlreadyExistsException extends IseiException {

    public UserAlreadyExistsException(String email) {
        super(String.format("User with such email already exists: %s", email), Code.USER_ALREADY_EXISTS);
    }
}
