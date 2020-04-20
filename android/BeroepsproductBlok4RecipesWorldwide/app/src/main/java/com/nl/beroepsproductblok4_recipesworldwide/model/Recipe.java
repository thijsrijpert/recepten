package com.nl.beroepsproductblok4_recipesworldwide.model;

public class Recipe {
    private String id, name, description, countrycode, username, mealtype_name, religion_id, timeOfDay;

    public Recipe(String id, String name, String description, String countrycode, String username) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.countrycode = countrycode;
        this.username = username;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
