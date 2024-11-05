package com.example.sudokutesttask.model;

import com.example.sudokutesttask.model.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sudoku {

    private short[][] grid;
    private Difficulty difficulty;
}
