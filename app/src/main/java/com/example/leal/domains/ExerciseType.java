package com.example.leal.domains;

public enum ExerciseType {
    AEROBIC("Aeróbico"), BODYBUILDING("Musculação");

    private final String description;

    ExerciseType(String description ) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
