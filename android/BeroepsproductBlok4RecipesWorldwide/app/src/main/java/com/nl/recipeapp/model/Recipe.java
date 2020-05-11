package com.nl.recipeapp.model;

import java.io.Serializable;

public class Recipe implements Serializable {
    private String id, name, description, countryCode, username, mealtypeName, religionId, timeOfDay;
    private boolean isApproved;

    public Recipe(String id, String name, String description, String countryCode, String username, String mealtypeName, String religionId, String timeOfDay) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.countryCode = countryCode;
        this.username = username;
        this.mealtypeName = mealtypeName;
        this.religionId = religionId;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMealtypeName() {
        return mealtypeName;
    }

    public void setMealtypeName(String mealtypeName) {
        this.mealtypeName = mealtypeName;
    }

    public String getReligionId() {
        return religionId;
    }

    public void setReligionId(String religionId) {
        this.religionId = religionId;
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
