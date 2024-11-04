package com.example.sudokutesttask.service;

import com.example.sudokutesttask.exception.InvalidLevelException;
import com.example.sudokutesttask.exception.SudokuReadException;
import com.example.sudokutesttask.model.Sudoku;
import com.example.sudokutesttask.util.SudokuReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class LocalFileSudokuProviderTest {

    @Autowired
    private LocalFileSudokuProvider localFileSudokuProvider;

    @Autowired
    private SudokuReader sudokuReader;

    @Test
    public void given_file_provider_when_invalid_level_number_then_throw() {
        var exception = assertThrows(InvalidLevelException.class, () -> localFileSudokuProvider.getSudoku(-1));
        assertEquals("Could not find level with number -1.", exception.getMessage());
    }

    @Test
    public void given_file_provider_when_no_levels_exist_then_throw() {
        var previousDataDirValue = ReflectionTestUtils.getField(sudokuReader, "dataDirectory");
        ReflectionTestUtils.setField(sudokuReader, "dataDirectory", "src\\test\\resources");
        var exception = assertThrows(InvalidLevelException.class, () -> localFileSudokuProvider.getRandomSudoku());
        assertEquals("No levels were found. Please verify that path to the file storage is correct.", exception.getMessage());
        ReflectionTestUtils.setField(sudokuReader, "dataDirectory", previousDataDirValue);
    }

    @Test
    public void given_file_provider_when_invalid_data_dir_then_throw() {
        var previousDataDirValue = ReflectionTestUtils.getField(sudokuReader, "dataDirectory");
        ReflectionTestUtils.setField(sudokuReader, "dataDirectory", "\\un_existing\\local\\directory\\");
        var exception = assertThrows(SudokuReadException.class, () -> localFileSudokuProvider.getRandomSudoku());
        assertEquals("Invalid data directory was provided. Please check the configuration.", exception.getMessage());
        ReflectionTestUtils.setField(sudokuReader, "dataDirectory", previousDataDirValue);
    }

    @Test
    public void given_file_provider_when_valid_level_number_is_provided_then_valid_return() {
        Sudoku expected = new Sudoku(new byte[][]{
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1}
        });
        Sudoku sudoku = localFileSudokuProvider.getSudoku(1);
        assertEquals(expected.getGrid().length, sudoku.getGrid().length);
        for (int i = 0; i < sudoku.getGrid().length; i++) {
            assertEquals(expected.getGrid()[i].length, sudoku.getGrid()[i].length);
            for (int j = 0; j < sudoku.getGrid()[i].length; j++) {
                assertEquals(expected.getGrid()[i][j], sudoku.getGrid()[i][j]);
            }
        }
    }

    @Test
    public void given_file_provider_when_single_file_in_directory_then_valid_return() {
        Sudoku expected = new Sudoku(new byte[][]{
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1}
        });
        Sudoku sudoku = localFileSudokuProvider.getRandomSudoku();
        assertEquals(expected.getGrid().length, sudoku.getGrid().length);
        for (int i = 0; i < sudoku.getGrid().length; i++) {
            assertEquals(expected.getGrid()[i].length, sudoku.getGrid()[i].length);
            for (int j = 0; j < sudoku.getGrid()[i].length; j++) {
                assertEquals(expected.getGrid()[i][j], sudoku.getGrid()[i][j]);
            }
        }
    }
}
