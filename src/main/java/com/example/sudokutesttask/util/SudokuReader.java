package com.example.sudokutesttask.util;

import com.example.sudokutesttask.exception.SudokuReadException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Component
public class SudokuReader {

    @Getter
    @Value("${DATA_DIRECTORY}")
    private String dataDirectory;

    @Value("${FILE_SECTION_SPLITTER}")
    private String sectionSplitter;

    public byte[][] readFile(String filename) {
        var sudokuFilePath = Path.of(dataDirectory, filename);
        var result = new byte[9][9];
        try (var reader = Files.newBufferedReader(sudokuFilePath)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                if(lineNumber > 8) {
                    throw new SudokuReadException("Provided damaged sudoku file %s, which contains more than 9 lines.".formatted(filename));
                }
                byte[] valuesLine = Arrays.stream(line.split(sectionSplitter))
                        .mapToInt(Integer::valueOf)
                        .map(i -> (byte) i)
                        .collect(ByteArrayOutputStream::new,
                                (baos, i) -> baos.write((byte) i),
                                (baos1, baos2) -> baos1.write(baos2.toByteArray(), 0, baos2.size()))
                        .toByteArray();

                if (valuesLine.length != 9) {
                    throw new SudokuReadException("Provided corrupted sudoku file, line %d does not have exactly 9 elements in file : %s."
                            .formatted(lineNumber + 1, filename)
                    );
                }

                result[lineNumber++] = valuesLine;
            }

            return result;
        } catch (IOException io) {
            throw new SudokuReadException("Unable to read Sudoku from file with name %s.".formatted(filename), io);
        }
    }
}
