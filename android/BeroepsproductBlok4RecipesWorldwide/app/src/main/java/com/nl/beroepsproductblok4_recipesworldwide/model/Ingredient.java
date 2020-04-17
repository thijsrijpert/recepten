package com.nl.beroepsproductblok4_recipesworldwide.model;

public class Ingredient {
    private String name, description;
    private boolean isApproved;

    public Ingredient(String name, String description, boolean isApproved) {
        this.name = name;
        this.description = description;
        this.isApproved = isApproved;
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
}
