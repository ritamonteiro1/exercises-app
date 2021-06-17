package com.example.leal.domains;


import com.google.firebase.Timestamp;

import java.io.Serializable;

public class Training implements Serializable {
    private String documentId;
    private Long id;
    private String description;
    private Timestamp date;

    public Training() {
    }

    public Training(Long id, String description, Timestamp date) {
        this.documentId = documentId;
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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