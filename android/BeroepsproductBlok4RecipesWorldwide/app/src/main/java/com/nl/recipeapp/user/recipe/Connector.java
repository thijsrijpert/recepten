package com.nl.recipeapp.user.recipe;

import android.content.Context;

import com.nl.recipeapp.model.Recipe;

public class Connector {
    private Context context;
    private boolean result;

    private Manage manageUserRecipe;

    public Connector(Context context) {
        this.context = context;
    }

    public void getRecipesForSpecificUser(String username) {


    }

    public void setManageUserRecipe(com.nl.recipeapp.user.recipe.Manage manageUserRecipe) {
        this.manageUserRecipe = manageUserRecipe;
    }
}
