package com.nl.beroepsproductblok4_recipesworldwide.model;

public class Recipe {
    private String id, name, description, countrycode, username, mealtype_name, religion_id, timeOfDay;
    private boolean isApproved;

    public Recipe(String id, String name, String description, String countrycode, String username, String mealtype_name, String religion_id, String timeOfDay) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.countrycode = countrycode;
        this.username = username;
        this.mealtype_name = mealtype_name;
        this.religion_id = religion_id;
        this.timeOfDay = timeOfDay;
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

    public String getMealtype_name() {
        return mealtype_name;
    }

    public void setMealtype_name(String mealtype_name) {
        this.mealtype_name = mealtype_name;
    }

    public String getReligion_id() {
        return religion_id;
    }

    public void setReligion_id(String religion_id) {
        this.religion_id = religion_id;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(int approved) {
        if (approved == 0) {
            this.isApproved = false;
        } else {
            this.isApproved = true;
        }

    }
}
