package com.example.iseitest.exception;

public class NoSuchUserRoleException extends NoSuchEntityException {

    public NoSuchUserRoleException(String id) {
        super(String.format("User not found: %s", id), Code.USER_ROLE_NOT_FOUND);
    }
}
