package com.nl.recipeapp.model;

public class Country {
    private String countryCode, name, description;

    public Country(String countryCode, String name, String description) {
        this.countryCode = countryCode;
        this.name = name;
        this.description = description;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
