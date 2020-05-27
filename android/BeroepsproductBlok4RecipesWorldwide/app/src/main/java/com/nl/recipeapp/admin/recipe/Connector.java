package com.nl.recipeapp.admin.recipe;

import android.content.Context;

import com.nl.recipeapp.model.Ingredient;
import com.nl.recipeapp.model.Recipe;

import java.util.ArrayList;

public class Connector {
    private Context context;
    private boolean result;

    public Connector(Context context) {
        this.context = context;
    }

    /**
     * Approves a given recipe
     * @param recipe The recipe object
     * @return A true or false boolean, depending on whether approving succeeded or not
     */
    public boolean approveRecipe(Recipe recipe) {


        return result;
    }

    /**
     * Denies a given recipe
     * @param recipe The recipe object
     * @return A true or false boolean, depending on whether denying succeeded or not
     */
    public boolean denyRecipe(Recipe recipe) {


        return result;
    }

    /**
     * Updates a given recipe
     * @param recipe The recipe object
     * @return A true or false boolean, depending on whether updating succeeded or not
     */
    public boolean updateRecipe(Recipe recipe) {


        return result;
    }

    /**
     * Deletes a recipe with the given recipe ID
     * @param recipe The recipe that has to be deleted
     * @return A true or false boolean, depending on whether deleting the recipe succeeded or not
     */
    public boolean deleteRecipe(Recipe recipe) {


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

    /**
     * When the RecyclerView in ManageRecipes has to be filled with its corresponding ingredients, this method is called
     * @param recipeId The recipe ID that has to be used when getting its corresponding ingredients
     * @return An ArrayList<Ingredient> of Ingredients (both approved and unapproved)
     */
    public ArrayList<Ingredient> getIngredientsBoundToRecipe(String recipeId) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();



        return ingredients;
    }
}
