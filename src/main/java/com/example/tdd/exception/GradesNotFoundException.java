package com.example.tdd.exception;

public class GradesNotFoundException extends RuntimeException {
    public GradesNotFoundException(String message) {
        super(message);
    }
}
