package com.example.leal.domains.exercise;

import java.io.Serializable;

public class ExerciseRequest implements Serializable {
    private Long id;
    private String urlImage;
    private String observation;
    private String type;

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

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getObservation() {
        return observation;
    }

    public String getType() {
        return type;
    }
}
