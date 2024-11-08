package com.example.sudokutesttask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SudokuCheckRequest {

    private int row;
    private int col;
    private short value;
}
