package com.nl.recipeapp.admin.recipe;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Ingredient;
import com.nl.recipeapp.model.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Connector {
    private Context context;
    private boolean result;

    private ArrayList<Recipe> arraylist_unapprovedRecipes;
    private Manage manageRecipe;

    public Connector(Context context) {
        this.context = context;
        arraylist_unapprovedRecipes = new ArrayList<>();
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
    public void getUnapprovedRecipes(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Recipe.php?where=isApproved-eq-0", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_unapprovedRecipes.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Recipe recipe = gson.fromJson(object.toString(), Recipe.class);
                        arraylist_unapprovedRecipes.add(recipe);

                        System.out.println("ADMINISTRATOR ARRAYLIST SIZE: " + arraylist_unapprovedRecipes.size());
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Mealtypes to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "ManageRecipe":
                            manageRecipe.getArraylist_unapprovedRecipes().clear();
                            manageRecipe.getArraylist_unapprovedRecipes().addAll(arraylist_unapprovedRecipes);
                            manageRecipe.getArrayAdapter_unapprovedRecipes().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Connector (admin/recipe): Afgekeurde recepten konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
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

    public void setManageRecipe(Manage manageRecipe) {
        this.manageRecipe = manageRecipe;
    }
}
