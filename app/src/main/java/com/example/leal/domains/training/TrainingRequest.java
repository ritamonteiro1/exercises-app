package com.example.leal.domains.training;

import com.google.firebase.Timestamp;

public class TrainingRequest {
    private final Long id;
    private final String description;
    private final Timestamp date;

    public TrainingRequest(Long id, String description, Timestamp date) {
        this.id = id;
        this.description = description;
        this.date = date;
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
}
