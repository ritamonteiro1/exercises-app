package com.example.leal.domains.exercise;

import java.io.Serializable;

import static com.example.leal.constants.Constants.EMPTY;

public enum ExerciseType implements Serializable {
    AEROBIC("Aeróbico", "https://firebasestorage.googleapis.com/v0/b/" +
            "leal-app-firebase.appspot" +
            ".com/o/images%2Faerobic.jpg?alt=media&token=6a0cfda4-64a6-4879-8cd9-e7dc58c44019"),
    BODYBUILDING("Musculação", "https://firebasestorage" +
            ".googleapis.com/v0/b/leal-app-firebase.appspot.com/o/images%2Fbodybuilding" +
            ".jpg?alt=media&token=1c2edfed-3b68-4164-af20-afc4de5a2895"),
    INVALID("Inválido", EMPTY);

    private final String type;
    private final String urlImage;

    ExerciseType(String type, String urlImage) {
        this.type = type;
        this.urlImage = urlImage;
    }

    public String getType() {
        return type;
    }
    public String getUrlImage() {
        return urlImage;
    }

    public static ExerciseType toExerciseType(String type) {
        if (type.equals(AEROBIC.getType())) {
            return AEROBIC;
        } else if (type.equals(BODYBUILDING.getType())) {
            return BODYBUILDING;
        } else {
            return INVALID;
        }
    }
}