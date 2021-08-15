package com.thoughtworks.springbootemployee.exception;

public class NoEmployeeWithIDFoundException extends RuntimeException{
    private String message;

    public NoEmployeeWithIDFoundException(String message) {
        super(message);
    }
}
