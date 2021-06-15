package com.example.leal.domains;

import com.google.firebase.Timestamp;

public class Training {
    private String documentId;
    private Long id;
    private String description;
    private Timestamp date;

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

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
