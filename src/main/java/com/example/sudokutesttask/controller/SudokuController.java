package com.example.sudokutesttask.controller;

import com.example.sudokutesttask.exception.IllegalSudokuOperationException;
import com.example.sudokutesttask.exception.SudokuReadException;
import com.example.sudokutesttask.model.Sudoku;
import com.example.sudokutesttask.service.SudokuHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/sudoku")
@CrossOrigin(origins = {"http://localhost:4200", "https://127.0.0.1:4200"}, maxAge = 3600, allowCredentials = "true")
public class SudokuController {

    private SudokuHolder sudokuHolder;

    public SudokuController(SudokuHolder sudokuHolder) {
        this.sudokuHolder = sudokuHolder;
    }

    @GetMapping("/level/{number}")
    public RedirectView generateSudokuByLevel(@PathVariable int number) {
        try {
            sudokuHolder.generateSudokuByLevel(number);
        } catch (SudokuReadException e) {
            return new RedirectView("/error/404"); //replace with 404 page
        }
        return new RedirectView("/sudoku/solve");
    }

    @GetMapping("/random")
    public RedirectView generateRandomSudoku() {
        try {
            sudokuHolder.generateRandomSudoku();
        } catch (SudokuReadException e) {
            return new RedirectView("/error/404"); //replace with 404 page
        }
        return new RedirectView("/sudoku/solve");
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
}
