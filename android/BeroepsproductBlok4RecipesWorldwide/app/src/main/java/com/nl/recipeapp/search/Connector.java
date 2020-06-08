package com.nl.recipeapp.search;

import android.content.Context;
import android.widget.Toast;

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
    private ArrayList<Recipe> arraylist_recipes;

    private Search search;
    private String url;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     */
    public Connector(Context context) {
        this.context = context;
        arraylist_recipes = new ArrayList<>();
        url = "";
    }

    /**
     * Request a JsonArray response from the provided URL.
     * @param mealtype The mealtype name
     * @param country The countrycode
     * @param religion The religion id
     * @param timeOfDay The time of day name
     */
    public void searchRecipe(String mealtype, String country, String religion, String timeOfDay) {
        // Clear the ArrayList of Recipes, so recipes from a previous search aren't included anymore

        // tablename.php --> selecteert alles uit de tabel
        // tablename.php?select=kolomnaam --> selecteert alles uit een specifieke kolom (kolomnaam)
        // tablename.php?select=kolomnaam&where=kolomnaam-eq-waardenaam --> selecteert alles uit een specifieke kolom, waar de waarde uit de kolom gelijk is aan de waardenaam

        url = "https://beroepsproduct.rijpert-webdesign.nl/api/Recipe.php?&where=isApproved-eq-1";
        String url_mealtype = ".mealtype_name-eq-" + mealtype;
        String url_country = ".countrycode-eq-" + country;
        String url_religion = ".religion_id-eq-" + religion;
        String url_timeOfDay = ".time_of_day-eq-" + timeOfDay;

        if (!mealtype.equals("null")) {
            url += url_mealtype;
        }

        if (!country.equals("null")) {
            url += url_country;
        }

        if (!religion.equals("null")) {
            url += url_religion;
        }

        if (!timeOfDay.equals("null")) {
            url += url_timeOfDay;
        }

        System.out.println(url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                System.out.println("Response: " + response.toString());
                Gson gson = new Gson();

                arraylist_recipes.clear();

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Recipe recipe = gson.fromJson(object.toString(), Recipe.class);
                        arraylist_recipes.add(recipe);
                    }

                    search.getArrayList_recipes().clear();
                    search.getArrayList_recipes().addAll(arraylist_recipes);
                    search.getRecyclerviewAdapter().notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Er zijn geen recepten met deze kenmerken gevonden.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    public void setSearch(Search search) {
        this.search = search;
    }
}
