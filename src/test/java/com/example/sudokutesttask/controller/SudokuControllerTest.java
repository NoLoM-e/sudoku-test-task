package com.example.sudokutesttask.controller;

import com.example.sudokutesttask.model.Sudoku;
import com.example.sudokutesttask.model.enums.Difficulty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SudokuControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void given_sudoku_controller_when_get_before_generate_then_400_response() {
        var response = restTemplate.getForEntity("http://localhost:" + port + "/sudoku/solve", Sudoku.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void given_sudoku_controller_when_get_after_random_generate_then_200_response() {
        Sudoku expected = new Sudoku(new short[][]{
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1}
        }, Difficulty.EASY);

        ResponseEntity<Sudoku> response = restTemplate.getForEntity("http://localhost:" + port + "/sudoku/random", Sudoku.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(expected.getGrid().length, response.getBody().getGrid().length);
        assertEquals(expected.getDifficulty(), response.getBody().getDifficulty());
        var numberOfFieldsMatched = 0;
        for (int i = 0; i < response.getBody().getGrid().length; i++) {
            assertEquals(expected.getGrid()[i].length, response.getBody().getGrid()[i].length);
            for (int j = 0; j < response.getBody().getGrid().length; j++) {
                if (expected.getGrid()[i][j] == response.getBody().getGrid()[i][j]) {
                    numberOfFieldsMatched++;
                }
            }
        }
        assertEquals(expected.getDifficulty().getFieldsToPopulate(), numberOfFieldsMatched);
    }

    @Test
    public void given_sudoku_controller_when_get_after_generate_valid_level_then_200_response() {
        Sudoku expected = new Sudoku(new short[][]{
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1},
                {9,8,7,6,5,4,3,2,1}
        }, Difficulty.EASY);

        ResponseEntity<Sudoku> response = restTemplate.getForEntity("http://localhost:" + port + "/sudoku/level/1", Sudoku.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(expected.getGrid().length, response.getBody().getGrid().length);
        assertEquals(expected.getDifficulty(), response.getBody().getDifficulty());
        var numberOfFieldsMatched = 0;
        for (int i = 0; i < response.getBody().getGrid().length; i++) {
            assertEquals(expected.getGrid()[i].length, response.getBody().getGrid()[i].length);
            for (int j = 0; j < response.getBody().getGrid().length; j++) {
                if (expected.getGrid()[i][j] == response.getBody().getGrid()[i][j]) {
                    numberOfFieldsMatched++;
                }
            }
        }
        assertEquals(expected.getDifficulty().getFieldsToPopulate(), numberOfFieldsMatched);
    }

    @Test
    public void given_sudoku_controller_when_generate_invalid_level_then_404_response() {
        var response = restTemplate.getForEntity("http://localhost:" + port + "/sudoku/level/-111", Sudoku.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody().getGrid());
    }
}
