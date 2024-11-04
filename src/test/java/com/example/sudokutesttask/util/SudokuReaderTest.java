package com.example.sudokutesttask.util;

import com.example.sudokutesttask.exception.SudokuReadException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class SudokuReaderTest {

    @Autowired
    private SudokuReader sudokuReader;

    @Test
    public void given_sudoku_reader_when_file_not_exist_then_throw() {
        var exception = assertThrows(SudokuReadException.class, () -> sudokuReader.readFile("non_existing_sudoku.csv"));
        assertEquals("Unable to read Sudoku from file with name non_existing_sudoku.csv.", exception.getMessage());
    }

    @Test
    public void given_sudoku_reader_when_more_than_9_lines_then_throw() {
        var exception = assertThrows(SudokuReadException.class, () -> sudokuReader.readFile("more_than_9_lines.csv"));
        assertEquals("Provided damaged sudoku file more_than_9_lines.csv, which contains more than 9 lines.", exception.getMessage());
    }

    @Test
    public void given_sudoku_reader_when_more_than_9_sections_then_throw() {
        var exception = assertThrows(SudokuReadException.class, () -> sudokuReader.readFile("more_than_9_sections.csv"));
        assertEquals("Provided corrupted sudoku file, line 4 does not have exactly 9 elements in file : more_than_9_sections.csv.", exception.getMessage());
    }

    @Test
    public void given_sudoku_reader_when_valid_file_then_valid_return() {
        byte[][] expected = {
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9}
        };
        byte[][] sudoku = sudokuReader.readFile("valid_sudoku_file.csv");
        assertEquals(expected.length, sudoku.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].length, sudoku[i].length);
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], sudoku[i][j]);
            }
        }
    }
}
