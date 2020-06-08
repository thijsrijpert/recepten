package com.nl.recipeapp;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.nl.recipeapp.model.User;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesManager {
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private static SharedPreferencesManager sharedPreferencesManager;

    private SharedPreferencesManager(Activity activity) {
        this.sharedPreferences = activity.getSharedPreferences("Preferences", MODE_PRIVATE);
        gson = new Gson();
    }

    public static SharedPreferencesManager getInstance(Activity activity) {
        if (sharedPreferencesManager == null) {
            sharedPreferencesManager = new SharedPreferencesManager(activity);
        }
        return sharedPreferencesManager;
    }

    public User getPref() {
        String json = sharedPreferences.getString("USER", null);
        return gson.fromJson(json, User.class);
    }

    public void removePref(String key) {
        sharedPreferences.edit().remove("USER").apply();
    }

    public void storeObjectInPref(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(user);
        editor.putString("USER", json);
        editor.apply();
    }
}
