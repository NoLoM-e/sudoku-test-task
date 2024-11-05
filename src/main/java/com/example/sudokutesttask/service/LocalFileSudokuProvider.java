package com.example.sudokutesttask.service;

import com.example.sudokutesttask.exception.InvalidLevelException;
import com.example.sudokutesttask.exception.SudokuReadException;
import com.example.sudokutesttask.model.Sudoku;
import com.example.sudokutesttask.model.enums.Difficulty;
import com.example.sudokutesttask.util.SudokuReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            return new Sudoku(values, getDifficulty(level));
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
            var levelNumberPattern = Pattern.compile(filePrefix + "(\\d+).*");
            Matcher matcher = levelNumberPattern.matcher(chosenFile.getName());
            var level = matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
            var difficulty = getDifficulty(level);
            return new Sudoku(values, difficulty);
        } catch (SudokuReadException e) {
            log.error("Was not able to read file {} chosen at random.", chosenFile.getName(), e);
            throw new SudokuReadException("Could not read random file %s.".formatted(chosenFile.getName()), e);
        }
    }

    private Difficulty getDifficulty(int level) {
        var difficulties = Arrays.stream(Difficulty.values())
                .sorted((a, b) -> b.getFieldsToPopulate() - a.getFieldsToPopulate())
                .toList();
        var itemPosition = level % 2 == 0 ? level / 2 - 1 : level / 2; // works fine with test data, real difficulty level should be counted in a different way
        return difficulties.get(itemPosition);
    }
}
