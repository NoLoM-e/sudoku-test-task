package com.example.sudokutesttask.util;

import com.example.sudokutesttask.exception.SudokuReadException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Component
public class SudokuReader {

    @Getter
    @Value("${DATA_DIRECTORY}")
    private String dataDirectory;

    @Value("${FILE_SECTION_SPLITTER}")
    private String sectionSplitter;

    public short[][] readFile(String filename) {
        var sudokuFilePath = Path.of(dataDirectory, filename);
        var result = new short[9][9];
        try (var reader = Files.newBufferedReader(sudokuFilePath)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                if(lineNumber > 8) {
                    throw new SudokuReadException("Provided damaged sudoku file %s, which contains more than 9 lines.".formatted(filename));
                }
                List<Short> valuesLine = Arrays.stream(line.split(sectionSplitter))
                        .map(Short::valueOf)
                        .toList();

                if (valuesLine.size() != 9) {
                    throw new SudokuReadException("Provided corrupted sudoku file, line %d does not have exactly 9 elements in file : %s."
                            .formatted(lineNumber + 1, filename)
                    );
                }

                for (int i = 0; i < 9; i++) {
                    result[lineNumber][i] = valuesLine.get(i);
                }
                lineNumber++;
            }

            return result;
        } catch (IOException io) {
            throw new SudokuReadException("Unable to read Sudoku from file with name %s.".formatted(filename), io);
        }
    }
}
