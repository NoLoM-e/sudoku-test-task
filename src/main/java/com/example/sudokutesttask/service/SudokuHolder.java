package com.example.sudokutesttask.service;

import com.example.sudokutesttask.exception.IllegalSudokuOperationException;
import com.example.sudokutesttask.model.Sudoku;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Arrays;
import java.util.Random;

@Component
@SessionScope
public class SudokuHolder {

    private SudokuProvider provider;

    private Sudoku sudoku;

    private Sudoku userSudoku;

    private Random random;

    public SudokuHolder(SudokuProvider provider, Random random) {
        this.provider = provider;
        this.random = random;
    }

    public void generateSudokuByLevel(int level) {
        this.sudoku = provider.getSudoku(level);
        this.userSudoku = populateUserSudoku(this.sudoku);
    }

    public void generateRandomSudoku() {
        this.sudoku = provider.getRandomSudoku();
        this.userSudoku = populateUserSudoku(this.sudoku);
    }

    public Sudoku getSudoku() {
        if (sudoku == null) {
            throw new IllegalSudokuOperationException("Sudoku must be initialized before it can be retrieved");
        }
        return sudoku;
    }

    public Sudoku getUserSudoku() {
        if (userSudoku == null) {
            throw new IllegalSudokuOperationException("Sudoku must be initialized before it can be retrieved");
        }
        return userSudoku;
    }

    private Sudoku populateUserSudoku(Sudoku sudoku) {
        var userSudoku = new Sudoku();
        userSudoku.setDifficulty(sudoku.getDifficulty());
        userSudoku.setGrid(Arrays.stream(sudoku.getGrid()).map(short[]::clone).toArray(short[][]::new));
        var numberOfFields = sudoku.getDifficulty().getFieldsToPopulate();
        for (int i = 0; i < Math.pow(sudoku.getGrid().length, 2) - numberOfFields; i++) {
            var row = random.nextInt(9);
            var column = random.nextInt(9);
            if (userSudoku.getGrid()[row][column] != 0) {
                userSudoku.getGrid()[row][column] = 0;
            } else {
                i--;
            }
        }

        return userSudoku;
    }

    public boolean checkMove(int row, int column, short value) {
        if (sudoku.getGrid()[row][column] == value) {
            userSudoku.getGrid()[row][column] = value; // to keep between reloads
            return true;
        }
        return false;
    }
}
