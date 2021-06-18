package com.example.leal.domains;

public class ExerciseResponse {
    private Long id;
    private String urlImage;
    private String observation;
    private String type;
    private String documentId;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Long getId() {
        return id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getObservation() {
        return observation;
    }

    public String getType() {
        return type;
    }
}
