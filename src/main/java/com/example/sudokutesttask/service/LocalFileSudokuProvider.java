package com.example.sudokutesttask.service;

import com.example.sudokutesttask.exception.InvalidLevelException;
import com.example.sudokutesttask.exception.SudokuReadException;
import com.example.sudokutesttask.model.Sudoku;
import com.example.sudokutesttask.util.SudokuReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Random;

@Component
@Scope(scopeName = "prototype")
@Slf4j
public class LocalFileSudokuProvider implements SudokuProvider {

    private SudokuReader sudokuReader;

    private Random randomGenerator;

    @Value("${SUDOKU_FILE_PREFIX}")
    private String filePrefix;

    private LocalFileSudokuProvider(SudokuReader sudokuReader, Random randomGenerator) {
        this.sudokuReader = sudokuReader;
        this.randomGenerator = randomGenerator;
    }

    @Override
    public Sudoku getSudoku(int level) {
        var filePattern = filePrefix + "%d" + ".csv";
        var fileName = String.format(filePattern, level);
        try {
            var values = sudokuReader.readFile(fileName);
            return new Sudoku(values);
        } catch (SudokuReadException e) {
            log.error("Was not able to read level {} from file {}", level, fileName, e);
            throw new InvalidLevelException("Could not find level with number %d.".formatted(level), e);
        }
    }

    @Override
    public Sudoku getRandomSudoku() {
        var dataDirectory = sudokuReader.getDataDirectory();
        var dataDirectoryFile = new File(dataDirectory);
        if (!dataDirectoryFile.exists() || !dataDirectoryFile.isDirectory()) {
            throw new SudokuReadException("Invalid data directory was provided. Please check the configuration.");
        }
        if (dataDirectoryFile.listFiles() == null ||
                dataDirectoryFile.listFiles().length == 0 ||
                dataDirectoryFile.listFiles((dir, name) -> name.endsWith(".csv") && name.startsWith(filePrefix)).length == 0
        ) {
            throw new InvalidLevelException("No levels were found. Please verify that path to the file storage is correct.");
        }
        var levelFiles = List.of(dataDirectoryFile.listFiles((dir, name) -> name.endsWith(".csv") && name.startsWith(filePrefix)));
        var randomNumber = randomGenerator.nextInt(levelFiles.size());
        var chosenFile = levelFiles.get(randomNumber);

        try {
            var values = sudokuReader.readFile(chosenFile.getName());
            return new Sudoku(values);
        } catch (SudokuReadException e) {
            log.error("Was not able to read file {} chosen at random.", chosenFile.getName(), e);
            throw new SudokuReadException("Could not read random file %s.".formatted(chosenFile.getName()), e);
        }
    }
}
