package com.nl.recipeapp.admin.ingredients;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Ingredient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Connector {
    private Context context;
    private boolean result;
    private ArrayList<Ingredient> arraylist_approvedIngredients, arraylist_unapprovedIngredients;
    private Manage manageIngredients;
    private com.nl.recipeapp.admin.recipe.Manage manageRecipe;

    public Connector(Context context) {
        this.context = context;
        arraylist_approvedIngredients = new ArrayList<>();
        arraylist_unapprovedIngredients = new ArrayList<>();
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
     */
    public void getUnapprovedIngredients(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?where=isApproved-eq-0", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_unapprovedIngredients.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Ingredient ingredient = gson.fromJson(object.toString(), Ingredient.class);
                        arraylist_unapprovedIngredients.add(ingredient);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Mealtypes to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "ManageIngredient":
                            manageIngredients.getArraylist_unapprovedIngredients().clear();
                            manageIngredients.getArraylist_unapprovedIngredients().addAll(arraylist_unapprovedIngredients);

                            manageIngredients.getArraylist_unapprovedIngredientNames().clear();
                            for (int c = 0; c < arraylist_unapprovedIngredients.size(); c++) {
                                manageIngredients.getArraylist_unapprovedIngredientNames().add(arraylist_unapprovedIngredients.get(c).getName());
                            }

                            manageIngredients.getArrayAdapter_unapprovedIngredients().notifyDataSetChanged();
                            break;
                        case "ManageRecipe":
                            manageRecipe.getArraylist_unapprovedIngredients().clear();
                            manageRecipe.getArraylist_unapprovedIngredients().addAll(arraylist_unapprovedIngredients);
                        break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("admin.ingredients.Connector: Afgekeurde ingrediënten konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    /**
     * When the Spinner in ManageRecipes has to be filled with approved recipes only (recipes that have the 'isApproved' column on '1'), this is the method which is called
     * @return An ArrayList<Recipe> which returns all approved recipes
     */
    public void getApprovedIngredients(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?where=isApproved-eq-1", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_approvedIngredients.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Ingredient ingredient = gson.fromJson(object.toString(), Ingredient.class);
                        arraylist_approvedIngredients.add(ingredient);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Mealtypes to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "ManageIngredient":
                            manageIngredients.getArraylist_approvedIngredients().clear();
                            manageIngredients.getArraylist_approvedIngredients().addAll(arraylist_approvedIngredients);

                            manageIngredients.getArraylist_approvedIngredientNames().clear();
                            for (int c = 0; c < arraylist_approvedIngredients.size(); c++) {
                                manageIngredients.getArraylist_approvedIngredientNames().add(arraylist_approvedIngredients.get(c).getName());
                            }

                            manageIngredients.getArrayAdapter_approvedIngredients().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("admin.ingredients.Connector: Goedgekeurde ingrediënten konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    public void setManageIngredients(Manage manageIngredients) {
        this.manageIngredients = manageIngredients;
    }

    public void setManageRecipe(com.nl.recipeapp.admin.recipe.Manage manageRecipe) {
        this.manageRecipe = manageRecipe;
    }
}
