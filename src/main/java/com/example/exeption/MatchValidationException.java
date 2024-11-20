package com.example.exeption;

public class MatchValidationException extends RuntimeException {
    public MatchValidationException(String message) {
        super(message);
    }
}
