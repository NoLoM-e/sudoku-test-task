package com.example.sudokutesttask.service;

import com.example.sudokutesttask.model.Sudoku;

public interface SudokuProvider {

    Sudoku getSudoku(int level);
    Sudoku getRandomSudoku();
}
