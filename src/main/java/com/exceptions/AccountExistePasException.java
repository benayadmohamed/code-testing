package com.exceptions;

public class AccountExistePasException extends RuntimeException {
    public AccountExistePasException(String message) {
        super(message);
    }
}
