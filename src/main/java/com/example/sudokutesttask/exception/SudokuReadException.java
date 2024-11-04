package com.example.sudokutesttask.exception;

public class SudokuReadException extends RuntimeException {

    public SudokuReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public SudokuReadException(String message) {
        super(message);
    }
}
