package com.example.leal.domains.training;


import com.google.firebase.Timestamp;

public class TrainingResponse {
    private String documentId;
    private Long id;
    private String description;
    private Timestamp date;

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getDocumentId() {
        return documentId;
    }
}