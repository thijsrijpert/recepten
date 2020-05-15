package com.nl.recipeapp.model;

public class Mealtype {
    private String name;

    public Mealtype(String name) {
        this.name = name;
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
