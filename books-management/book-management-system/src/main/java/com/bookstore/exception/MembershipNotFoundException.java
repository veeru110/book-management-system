package com.bookstore.exception;

public class MembershipNotFoundException extends RuntimeException {
    private String message;

    public MembershipNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
