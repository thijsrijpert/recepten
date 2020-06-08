package com.nl.recipeapp.user.recipe;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Connector {
    private Context context;
    private ArrayList<Recipe> arraylist_recipesForSpecificUser;

    private Manage manageUserRecipe;

    public Connector(Context context) {
        this.context = context;
        arraylist_recipesForSpecificUser = new ArrayList<>();
    }

    public void getRecipesForSpecificUser(String username, final String calledFrom) {
        // Request a string response from the provided URL.
        System.out.println("recipeapp.user.recipe.Connector: Username > " + username);
        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Recipe.php?where=username-eq-" + username + "", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_recipesForSpecificUser.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Recipe recipe = gson.fromJson(object.toString(), Recipe.class);
                        arraylist_recipesForSpecificUser.add(recipe);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Ingredients to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "ManageUserRecipe":
                            manageUserRecipe.getArrayList_recipes().clear();
                            manageUserRecipe.getArrayList_recipes().addAll(arraylist_recipesForSpecificUser);

                            manageUserRecipe.getArrayList_recipeNames().clear();
                            for (int c = 0; c < arraylist_recipesForSpecificUser.size(); c++) {
                                manageUserRecipe.getArrayList_recipeNames().add(arraylist_recipesForSpecificUser.get(c).getName());
                            }
                            manageUserRecipe.getArrayAdapter_recipeNames().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("recipeapp.user.recipe.Connector: Recepten voor specifieke gebruiker konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request2);

    }

    public void setManageUserRecipe(com.nl.recipeapp.user.recipe.Manage manageUserRecipe) {
        this.manageUserRecipe = manageUserRecipe;
    }
}
