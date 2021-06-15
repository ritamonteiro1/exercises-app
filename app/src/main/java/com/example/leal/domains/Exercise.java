package com.example.leal.domains;

public class Exercise {
    private int id;
    private String urlImage;
    private String observation;
    private ExerciseType type;

    public Exercise(int id, String urlImage, String observation, ExerciseType type) {
        this.id = id;
        this.urlImage = urlImage;
        this.observation = observation;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getObservation() {
        return observation;
    }

    public ExerciseType getType() {
        return type;
    }
}
