package com.nl.recipeapp.model;

public class User {
    private String username;
    private boolean isAdministrator;
    private String token;

    public User(String username, boolean isAdministrator) {
        this.username = username;
        this.isAdministrator = isAdministrator;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User()    {
    }
}