package com.example.leal.domains.training;

import java.io.Serializable;
import java.util.Date;

public class Training implements Serializable {
    private final String documentId;
    private final Long id;
    private final String description;
    private final Date date;

    public Training(Long id, String description, Date date, String documentId) {
        this.documentId = documentId;
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

    public Date getDate() {
        return date;
    }

    public String getDocumentId() {
        return documentId;
    }
}
