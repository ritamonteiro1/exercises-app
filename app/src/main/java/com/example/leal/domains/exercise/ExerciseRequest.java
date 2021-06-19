package com.example.leal.domains.exercise;

import java.io.Serializable;

public class ExerciseRequest implements Serializable {
    private final Long id;
    private final String urlImage;
    private final String observation;
    private final String type;

    public ExerciseRequest(Long id, String observation, String type,
                           String urlImage) {
        this.id = id;
        this.observation = observation;
        this.type = type;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getObservation() {
        return observation;
    }
}
