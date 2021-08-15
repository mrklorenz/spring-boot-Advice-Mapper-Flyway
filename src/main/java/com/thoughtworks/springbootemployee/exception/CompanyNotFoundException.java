package com.thoughtworks.springbootemployee.exception;

public class CompanyNotFoundException extends RuntimeException{
    private String message;
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
