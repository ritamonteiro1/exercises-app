package com.example.leal.domains.exercise;

import java.io.Serializable;

public class Exercise implements Serializable {
    private final Long id;
    private final String urlImage;
    private final String observation;
    private final ExerciseType type;
    private final String documentId;

    public Exercise(Long id, String observation, ExerciseType type, String documentId,
                    String urlImage) {
        this.id = id;
        this.observation = observation;
        this.type = type;
        this.documentId = documentId;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getDocumentId() {
        return documentId;
    }

    public Long getId() {
        return id;
    }

    public String getObservation() {
        return observation;
    }

    public ExerciseType getExerciseType() {
        return type;
    }
}
