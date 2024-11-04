package com.example.sudokutesttask.service;

import com.example.sudokutesttask.exception.IllegalSudokuOperationException;
import com.example.sudokutesttask.exception.InvalidLevelException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class SudokuHolderTest {

    @Autowired
    private SudokuHolder sudokuHolder;

    @Test
    public void given_holder_when_not_initialized_then_throw() {
        var exception = assertThrows(IllegalSudokuOperationException.class, () -> sudokuHolder.getSudoku());
        assertEquals("Sudoku must be initialized before it can be retrieved", exception.getMessage());
    }

    @Test
    public void given_holder_when_init_random_then_get_does_not_throw() {
        sudokuHolder.generateRandomSudoku();
        var sudoku = sudokuHolder.getSudoku();
        assertEquals(9, sudoku.getGrid().length);
        for (int i = 0; i < sudoku.getGrid().length; i++) {
            assertEquals(9, sudoku.getGrid()[i].length);
        }
    }

    @Test
    public void given_holder_when_init_valid_level_then_get_does_not_throw() {
        sudokuHolder.generateSudokuByLevel(1);
        var sudoku = sudokuHolder.getSudoku();
        assertEquals(9, sudoku.getGrid().length);
        for (int i = 0; i < sudoku.getGrid().length; i++) {
            assertEquals(9, sudoku.getGrid()[i].length);
        }
    }

    @Test
    public void given_holder_when_init_invalid_level_then_throw() {
        var exception = assertThrows(InvalidLevelException.class, () -> sudokuHolder.generateSudokuByLevel(-111));
        assertEquals("Could not find level with number -111.", exception.getMessage());
    }
}
