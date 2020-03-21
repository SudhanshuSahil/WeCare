package com.example.demoapp;

import java.util.List;

public class Patient {
    private String id;
    private String name;
    private String village;
    private int age;
    private float hieght;
    private float weight;
    private float bmi;
    private float haemobglobin;
    private float bloodsugar;
    private List<recommendation> reclist;




    public Patient(){ }

    public Patient(String id, String name, String village, int age, float hieght, float weight, float bmi, float haemobglobin, float bloodsugar) {
        this.id = id;
        this.name = name;
        this.village = village;
        this.age = age;
        this.hieght = hieght;
        this.weight = weight;
        this.bmi = bmi;
        this.haemobglobin = haemobglobin;
        this.bloodsugar = bloodsugar;
    }

    public List<recommendation> getReclist() {
        return reclist;
    }

    public void setReclist(List<recommendation> reclist) {
        this.reclist = reclist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHieght() {
        return hieght;
    }

    public void setHieght(float hieght) {
        this.hieght = hieght;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getHaemobglobin() {
        return haemobglobin;
    }

    public void setHaemobglobin(float haemobglobin) {
        this.haemobglobin = haemobglobin;
    }

    public float getBloodsugar() {
        return bloodsugar;
    }

    public void setBloodsugar(float bloodsugar) {
        this.bloodsugar = bloodsugar;
    }
}
