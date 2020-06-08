package com.nl.recipeapp.admin.ingredients;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Ingredient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Connector {
    private Context context;
    private ArrayList<Ingredient> arraylist_approvedIngredients, arraylist_unapprovedIngredients;
    private Manage manageIngredients;
    private com.nl.recipeapp.admin.recipe.Manage manageRecipe;

    public Connector(Context context) {
        this.context = context;
        arraylist_approvedIngredients = new ArrayList<>();
        arraylist_unapprovedIngredients = new ArrayList<>();
    }

    /**
     * Approves an Ingredient object and changes any fields the moderator wanted to change
     * @param ingredient The new Ingredient object
     * @param oldName The old name of the selected ingredient, used to update the correct ingredient.
     */
    public void approveIngredient(final Ingredient ingredient, String oldName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?set=name-" + ingredient.getName() + ".is_approved-1.description-" + ingredient.getDescription() + "&where=name-eq-" + oldName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Ingrediënt '" + ingredient.getName() + "' is goedgekeurd", Toast.LENGTH_SHORT).show();
                manageIngredients.initializeArrayLists();
                manageIngredients.updateViewContent_A();
                manageIngredients.updateViewContent_B();
                manageIngredients.getEdittexts(0).setText("");
                manageIngredients.getEdittexts(1).setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "admin.ingredients.Connector: Het ingrediënt kon niet worden goedgekeurd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    /**
     *
     * @param ingredient
     */
    public void denyIngredient(final Ingredient ingredient) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?delete=name-" + ingredient.getName(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Ingrediënt '" + ingredient.getName() + "' is verwijderd", Toast.LENGTH_SHORT).show();
                manageIngredients.getEdittexts(2).setText("");
                manageIngredients.getEdittexts(3).setText("");
                manageIngredients.initializeArrayLists();
                manageIngredients.updateViewContent_A();
                manageIngredients.updateViewContent_B();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "admin.ingredients.Connector: Het ingrediënt kon niet worden verwijderd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }


    public void updateIngredient(final String oldName, final String newName, String newDescription) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?set=name-"+ newName + ".description-" + newDescription + "&where=name-eq-" + oldName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Ingrediënt '" + oldName + "' succesvol gewijzigd naar '" + newName + "'.", Toast.LENGTH_SHORT).show();
                manageIngredients.getEdittexts(2).setText("");
                manageIngredients.getEdittexts(3).setText("");
                manageIngredients.initializeArrayLists();
                manageIngredients.updateViewContent_A();
                manageIngredients.updateViewContent_B();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "admin.ingredients.Connector: Het ingrediënt kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    /**
     * When the Spinner in ManageRecipes has to be filled with unapproved recipes only (recipes that have the 'isApproved' column on '0'), this is the method which is called
     */
    public void getUnapprovedIngredients(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?where=is_approved-eq-0", new JSONArray(), new Response.Listener<JSONArray>() {
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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?where=is_approved-eq-1", new JSONArray(), new Response.Listener<JSONArray>() {
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
