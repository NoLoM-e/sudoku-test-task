package com.example.sudokutesttask.model.enums;

import lombok.Getter;

public enum Difficulty {

    EASY(30),
    MEDIUM(25),
    AMATEUR(20),
    HARD(10),
    IMPOSSIBLE(5);

    @Getter
    final int fieldsToPopulate;

    Difficulty(int fields) {
        this.fieldsToPopulate = fields;
    }

    public static Difficulty fromNumberOfFields(int numberOfFields) {
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.getFieldsToPopulate() == numberOfFields) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Invalid number of fields to populate: " + numberOfFields);
    }
}
