package com.nl.recipeapp.admin.ingredients;

import android.content.Context;

import com.nl.recipeapp.model.Ingredient;

import java.util.ArrayList;

public class Connector {
    private Context context;
    private boolean result;

    public Connector(Context context) {
        this.context = context;
    }

    public boolean approveIngredient(Ingredient ingredient) {


        return result;
    }


    public boolean denyIngredient(Ingredient ingredient) {


        return result;
    }


    public boolean updateIngredient(Ingredient ingredient) {


        return result;
    }

    /**
     * When the Spinner in ManageRecipes has to be filled with unapproved recipes only (recipes that have the 'isApproved' column on '0'), this is the method which is called
     * @return An ArrayList<Recipe> which returns all unapproved recipes
     */
    public ArrayList<Ingredient> getUnapprovedIngredients() {
        ArrayList<Ingredient> unapprovedIngredients = new ArrayList<>();



        return unapprovedIngredients;
    }

    /**
     * When the Spinner in ManageRecipes has to be filled with approved recipes only (recipes that have the 'isApproved' column on '1'), this is the method which is called
     * @return An ArrayList<Recipe> which returns all approved recipes
     */
    public ArrayList<Ingredient> getApprovedIngredients() {
        ArrayList<Ingredient> approvedIngredients = new ArrayList<>();



        return approvedIngredients;
    }
}
