package com.example.leal.domains;

import java.io.Serializable;

public class Exercise implements Serializable {
    private int id;
    private String urlImage;
    private String observation;
    private ExerciseType type;
    private String documentId;

    public Exercise(int id, String observation, ExerciseType type, String documentId) {
        this.id = id;
        this.observation = observation;
        this.type = type;
        this.documentId = documentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getId() {
        return id;
    }

    public String getObservation() {
        return observation;
    }

    public ExerciseType getType() {
        return type;
    }
}
