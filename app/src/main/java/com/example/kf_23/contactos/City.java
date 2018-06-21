package com.example.kf_23.contactos;

public class City {

    private String name;
    private String state;
    private String country;
    private boolean capital;
    private long population;

    public City() {}

    public City(String name, String state, String country, boolean capital, long population) {
        // ...
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public boolean isCapital() {
        return capital;
    }

    public long getPopulation() {
        return population;
    }

}