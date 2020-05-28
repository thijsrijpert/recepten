package com.nl.recipeapp.model;

public class Religion {
    String id, name;

    public Religion(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Religion() {

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

    @Override
    public String toString() {
        return name;
    }
}
