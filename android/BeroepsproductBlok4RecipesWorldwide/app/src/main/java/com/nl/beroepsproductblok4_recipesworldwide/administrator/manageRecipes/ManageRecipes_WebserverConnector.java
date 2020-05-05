package com.nl.beroepsproductblok4_recipesworldwide.administrator.manageRecipes;

import android.content.Context;
import android.view.View;

import com.nl.beroepsproductblok4_recipesworldwide.model.Recipe;

import java.util.ArrayList;

public class ManageRecipes_WebserverConnector {
    private Context context;
    private boolean result;

    public ManageRecipes_WebserverConnector(Context context) {
        this.context = context;
    }

    public boolean approveRecipe(Recipe recipe) {


        return result;
    }

    /**
     * When the Spinner in ManageRecipes has to be filled with unapproved recipes only (recipes that have the 'isApproved' column on '0'), this is the method which is called
     * @return An ArrayList<Recipe> which returns all unapproved recipes
     */
    public ArrayList<Recipe> getUnapprovedRecipes() {
        ArrayList<Recipe> unapprovedRecipes = new ArrayList<>();



        return unapprovedRecipes;
    }

    /**
     * When the Spinner in ManageRecipes has to be filled with approved recipes only (recipes that have the 'isApproved' column on '1'), this is the method which is called
     * @return An ArrayList<Recipe> which returns all approved recipes
     */
    public ArrayList<Recipe> getApprovedRecipes() {
        ArrayList<Recipe> approvedRecipes = new ArrayList<>();



        return approvedRecipes;
    }
}
