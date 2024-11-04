package com.example.sudokutesttask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No such sudoku was found.")
public class SudokuReadException extends RuntimeException {

    public SudokuReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public SudokuReadException(String message) {
        super(message);
    }
}
