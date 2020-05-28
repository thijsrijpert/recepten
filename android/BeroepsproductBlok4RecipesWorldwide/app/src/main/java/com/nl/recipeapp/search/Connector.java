package com.nl.recipeapp.search;

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
    private ArrayList<Recipe> recipes;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     */
    public Connector(Context context) {
        this.context = context;
        recipes = new ArrayList<>();
    }

    /**
     * Request a JsonArray response from the provided URL.
     * @param mealtype The mealtype name
     * @param country The countrycode
     * @param religion The religion id
     * @param timeOfDay The time of day name
     * @return An ArrayList<Recipe> that contains the recipes as a result of the query
     */
    public ArrayList<Recipe> searchRecipe(String mealtype, String country, String religion, String timeOfDay) {
        // Clear the ArrayList of Recipes, so recipes from a previous search aren't included anymore
        recipes.clear();

        // tablename.php --> selecteert alles uit de tabel
        // tablename.php?select=kolomnaam --> selecteert alles uit een specifieke kolom (kolomnaam)
        // tablename.php?select=kolomnaam&where=kolomnaam-eq-waardenaam --> selecteert alles uit een specifieke kolom, waar de waarde uit de kolom gelijk is aan de waardenaam

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/test/api/recipe.php?select=mealtype_name-eq-" + mealtype + "&where=countrycode-eq-" + country + "&where=religion_id-eq-" + religion + "&where=time_of_day-eq-" + timeOfDay + "", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                System.out.println("Response: " + response.toString());
                Gson gson = new Gson();

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Recipe recipe = gson.fromJson(object.toString(), Recipe.class);

                        recipes.add(recipe);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: " + error.networkResponse.headers.toString());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);

        // Fill the ArrayList with the religions

        return recipes;
    }
}
