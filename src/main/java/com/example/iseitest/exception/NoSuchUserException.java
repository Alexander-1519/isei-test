package com.example.iseitest.exception;

public class NoSuchUserException extends NoSuchEntityException{

    public NoSuchUserException(String id) {
        super(String.format("User not found: %s", id), Code.USER_NOT_FOUND);
    }

}
