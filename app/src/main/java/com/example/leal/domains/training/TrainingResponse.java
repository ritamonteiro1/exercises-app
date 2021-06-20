package com.example.leal.domains.training;


import com.google.firebase.Timestamp;

import java.io.Serializable;

public class TrainingResponse implements Serializable {
    private String documentId;
    private Long id;
    private String description;
    private Timestamp date;

    public TrainingResponse() {
    }

    public TrainingResponse(Long id, String description, Timestamp date) {
        this.documentId = documentId;
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

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