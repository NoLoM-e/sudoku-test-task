package com.example.sudokutesttask.service;

import com.example.sudokutesttask.model.Sudoku;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class LocalFileSudokuProvider implements SudokuProvider {

    @Override
    public Sudoku getSudoku(int level) {
        return null;
    }

    @Override
    public Sudoku getRandomSudoku() {
        return null;
    }
}
