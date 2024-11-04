package com.example.sudokutesttask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid operation on sudoku.")
public class IllegalSudokuOperationHttpException extends HttpStatusCodeException {
    public IllegalSudokuOperationHttpException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
