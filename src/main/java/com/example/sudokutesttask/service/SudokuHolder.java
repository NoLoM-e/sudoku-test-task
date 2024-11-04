package com.example.sudokutesttask.service;

import com.example.sudokutesttask.exception.IllegalSudokuOperationException;
import com.example.sudokutesttask.model.Sudoku;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SudokuHolder {

    private SudokuProvider provider;

    private Sudoku sudoku;

    public SudokuHolder(SudokuProvider provider) {
        this.provider = provider;
    }

    public void generateSudokuByLevel(int level) {
        this.sudoku = provider.getSudoku(level);
    }

    public void generateRandomSudoku() {
        this.sudoku = provider.getRandomSudoku();
    }

    public Sudoku getSudoku() {
        if (sudoku == null) {
            throw new IllegalSudokuOperationException("Sudoku must be initialized before it can be retrieved");
        }
        return sudoku;
    }
}
