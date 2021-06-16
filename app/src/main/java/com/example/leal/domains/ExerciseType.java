package com.example.leal.domains;

import java.io.Serializable;

public enum ExerciseType implements Serializable {
    AEROBIC("Aeróbico"), BODYBUILDING("Musculação"), INVALID("Inválido");

    private final String description;

    ExerciseType(String description ) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ExerciseType toExerciseType(String type){

        if (type.equals(AEROBIC.description)){
            return AEROBIC;
        } else if (type.equals(BODYBUILDING.description)) {
            return BODYBUILDING;
        } else {
            return INVALID;
        }
    }
}
