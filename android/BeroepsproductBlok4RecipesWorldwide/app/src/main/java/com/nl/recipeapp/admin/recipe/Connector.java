package com.nl.recipeapp.admin.recipe;

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
import com.nl.recipeapp.model.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Connector {
    private Context context;

    private ArrayList<Recipe> arraylist_unapprovedRecipes;
    private ArrayList<Recipe> arraylist_approvedRecipes;
    private Manage manageRecipe;

    public Connector(Context context) {
        this.context = context;
        arraylist_unapprovedRecipes = new ArrayList<>();
        arraylist_approvedRecipes = new ArrayList<>();
    }

    /**
     * Approves a given recipe
     * @param recipe The recipe object
     */
    public void approveRecipe(final Recipe recipe) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Recipe.php?set=isApproved-1&where=id-eq-" + recipe.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Recept '" + recipe.getName() + "' is goedgekeurd", Toast.LENGTH_SHORT).show();
                manageRecipe.initializeArrayLists();
                manageRecipe.updateViewContent_A();
                manageRecipe.updateViewContent_B();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "admin.recipe.Connector: Het recept kon niet worden goedgekeurd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    /**
     * Updates a given recipe
     * @param recipe The recipe object
     */
    public void updateRecipe(final Recipe recipe) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Recipe.php?set=name-"+ recipe.getName() + ".description-" + recipe.getDescription() + ".isApproved-" + recipe.isApproved() + ".countrycode-" + recipe.getCountryCode() + ".mealtype_name-" + recipe.getMealtypeName() + ".religion_id-" + recipe.getReligionId() + ".time_of_day-" + recipe.getTimeOfDay() + "&where=id-eq-" + recipe.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Recept '" + recipe.getName() + "' is succesvol gewijzigd", Toast.LENGTH_SHORT).show();
                manageRecipe.initializeArrayLists();
                manageRecipe.updateViewContent_A();
                manageRecipe.updateViewContent_B();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "admin.recipe.Connector: Het recept kon niet worden gewijzigd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    /**
     * Deletes a recipe with the given recipe ID
     * @param recipe The recipe that has to be deleted
     */
    public void deleteRecipe(final Recipe recipe) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Recipe.php?delete=id-" + recipe.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Recept '" + recipe.getName() + "' is succesvol verwijderd", Toast.LENGTH_SHORT).show();
                manageRecipe.initializeArrayLists();
                manageRecipe.updateViewContent_A();
                manageRecipe.updateViewContent_B();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "admin.recipe.Connector: Het recept kon niet worden verwijderd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
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

                            manageRecipe.getArraylist_unapprovedRecipeNames().clear();
                            for (int c = 0; c < arraylist_unapprovedRecipes.size(); c++) {
                                manageRecipe.getArraylist_unapprovedRecipeNames().add(arraylist_unapprovedRecipes.get(c).getName());
                            }

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
    public void getApprovedRecipes(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Recipe.php?where=isApproved-eq-1", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_approvedRecipes.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Recipe recipe = gson.fromJson(object.toString(), Recipe.class);
                        arraylist_approvedRecipes.add(recipe);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Mealtypes to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "ManageRecipe":
                            manageRecipe.getArraylist_approvedRecipes().clear();
                            manageRecipe.getArraylist_approvedRecipes().addAll(arraylist_approvedRecipes);

                            manageRecipe.getArraylist_approvedRecipeNames().clear();
                            for (int c = 0; c < arraylist_approvedRecipes.size(); c++) {
                                manageRecipe.getArraylist_approvedRecipeNames().add(arraylist_approvedRecipes.get(c).getName());
                            }

                            manageRecipe.getArrayAdapter_approvedRecipes().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Connector (admin/recipe): Goedgekeurde recepten konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
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
