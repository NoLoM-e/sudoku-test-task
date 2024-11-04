package com.example.sudokutesttask.exception;

public class InvalidLevelException extends RuntimeException {

    public InvalidLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLevelException(String message) {
        super(message);
    }
}
