package com.example.leal.domains;

import java.io.Serializable;

public class ExerciseRequest implements Serializable {
    private Long id;
    private String urlImage;
    private String observation;
    private ExerciseType type;
    private String documentId;

    public ExerciseRequest(Long id, String observation, ExerciseType type, String documentId,
                           String urlImage) {
        this.id = id;
        this.observation = observation;
        this.type = type;
        this.documentId = documentId;
        this.urlImage = urlImage;
    }

    public ExerciseRequest(Long id, String observation, ExerciseType type,
                           String urlImage) {
        this.id = id;
        this.observation = observation;
        this.type = type;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Long getId() {
        return id;
    }

    public String getObservation() {
        return observation;
    }

    public ExerciseType getType() {
        return type;
    }
}
