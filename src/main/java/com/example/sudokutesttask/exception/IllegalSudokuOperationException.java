package com.example.sudokutesttask.exception;

public class IllegalSudokuOperationException extends RuntimeException {

    public IllegalSudokuOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalSudokuOperationException(String message) {
        super(message);
    }
}
