package com.nl.recipeapp.model;

public class User {
    private String username, userRole;
    private boolean isAdministrator;
    private String token;

    public User(String username, String userRole) {
        this.username = username;
        this.userRole = userRole;

        if (userRole.equals("administrator")) {
            setAdministrator(true);
        } else {
            setAdministrator(false);
        }
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}