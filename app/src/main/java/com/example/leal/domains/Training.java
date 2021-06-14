package com.example.leal.domains;

import java.util.List;

public class Training {
    private int id;
    private String description;
    private Long date;
    private List<Exercise> exerciseList;

    public Training(int id, String description, Long date) {
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Long getDate() {
        return date;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }
}
