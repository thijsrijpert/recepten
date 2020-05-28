package com.nl.recipeapp.model;

public class Ingredient {
    private String name, description, username;
    private boolean isApproved;

    public Ingredient(String name, String description, boolean isApproved, String username) {
        this.name = name;
        this.description = description;
        this.isApproved = isApproved;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
