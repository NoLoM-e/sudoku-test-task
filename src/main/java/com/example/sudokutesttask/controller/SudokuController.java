package com.example.sudokutesttask.controller;

import com.example.sudokutesttask.exception.IllegalSudokuOperationException;
import com.example.sudokutesttask.exception.SudokuReadException;
import com.example.sudokutesttask.model.Sudoku;
import com.example.sudokutesttask.model.SudokuCheckRequest;
import com.example.sudokutesttask.service.SudokuHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sudoku")
@CrossOrigin(
        origins = {"http://localhost:4200", "http://127.0.0.1:4200"},
        maxAge = 3600,
        allowCredentials = "true",
        exposedHeaders = {"X-Redirect-Location"}
)
@Slf4j
public class SudokuController {

    private SudokuHolder sudokuHolder;

    public SudokuController(SudokuHolder sudokuHolder) {
        this.sudokuHolder = sudokuHolder;
    }

    @GetMapping("/level/{number}")
    public ResponseEntity<Boolean> generateSudokuByLevel(@PathVariable int number){
        try {
            sudokuHolder.generateSudokuByLevel(number);
        } catch (SudokuReadException e) {
            return ResponseEntity.badRequest().body(false);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Redirect-Location", "/solve")
                .body(true);
    }

    @GetMapping("/random")
    public ResponseEntity<Boolean>  generateRandomSudoku() {
        try {
            sudokuHolder.generateRandomSudoku();
        } catch (SudokuReadException e) {
            return ResponseEntity.badRequest().body(false);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Redirect-Location", "/solve")
                .body(true);
    }

    @GetMapping("/solve")
    public ResponseEntity<Sudoku> solveSudoku() {
        try {
            return ResponseEntity.ok(sudokuHolder.getUserSudoku());
        } catch (IllegalSudokuOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (SudokuReadException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/check")
    public ResponseEntity<Boolean> checkMove(@RequestBody SudokuCheckRequest request) {
        return ResponseEntity.ok(sudokuHolder.checkMove(request.getRow(), request.getCol(), request.getValue()));
    }
}
