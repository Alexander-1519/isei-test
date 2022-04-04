package com.example.iseitest.exception;

public class NoSuchUserException extends NoSuchEntityException{

    public NoSuchUserException(String email) {
        super(String.format("User not found: %s", email), Code.USER_NOT_FOUND);
    }

    public NoSuchUserException(Long id) {
        super(String.format("User not found: %s", id), Code.USER_NOT_FOUND);
    }
}
