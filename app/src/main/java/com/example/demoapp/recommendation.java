package com.example.demoapp;

public class recommendation {
    private String doctor_name;
    private String message;

    public recommendation(){}

    public recommendation(String doctor_name, String message) {
        this.doctor_name = doctor_name;
        this.message = message;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
