package com.nl.recipeapp.model;

import java.io.Serializable;

public class Recipe implements Serializable {
    private String id, name, description, countrycode, username, mealtype_name, religion_id, time_of_day, isApproved;
//    private boolean isApproved;

    public Recipe(String id, String name, String description, String countrycode, String username, String mealtype_name, String religion_id, String time_of_day, String isApproved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.countrycode = countrycode;
        this.username = username;
        this.mealtype_name = mealtype_name;
        this.religion_id = religion_id;
        this.time_of_day = time_of_day;
        this.isApproved = isApproved;
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

    public String getCountryCode() {
        return countrycode;
    }

    public void setCountryCode(String countryCode) {
        this.countrycode = countryCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMealtypeName() {
        return mealtype_name;
    }

    public void setMealtypeName(String mealtype_name) {
        this.mealtype_name = mealtype_name;
    }

    public String getReligionId() {
        return religion_id;
    }

    public void setReligionId(String religion_id) {
        this.religion_id = religion_id;
    }

    public String getTimeOfDay() {
        return time_of_day;
    }

    public void setTimeOfDay(String time_of_day) {
        this.time_of_day = time_of_day;
    }

    public String isApproved() {
        return isApproved;
    }

    public void setApproved(String approved) {
        isApproved = approved;
    }
}
