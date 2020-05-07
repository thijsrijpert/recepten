package com.nl.recipeapp.recipe;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nl.recipeapp.R;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Country;
import com.nl.recipeapp.model.Ingredient;
import com.nl.recipeapp.model.Recipe;
import com.nl.recipeapp.model.Religion;

import java.util.ArrayList;

public class AddConnector {
    private Context context;
    private EditText edittext_recipeName;
    private boolean succesfullyAddedRecipe;
    private View view;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     */
    public AddConnector(Context context) {
        this.context = context;
    }

    public void initializeEditTexts() {
        edittext_recipeName = view.findViewById(R.id.addRecipe_edittext_recipeName);
    }

    /**
     * Adds a recipe to the database
     */
    public boolean addRecipe(Recipe recipe) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Recept '" + edittext_recipeName.getText() + "' succesvol aangemeld. Een administrator zal het beoordelen.", Toast.LENGTH_SHORT).show();
//              System.out.println(response);
                succesfullyAddedRecipe = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Het recept '" + edittext_recipeName.getText() + "' kon niet worden aangemeld.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
                succesfullyAddedRecipe = false;
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedRecipe;
    }

    /**
     * Gets ALL recipes from the database (mostly used for checking purposes).
     */
    public ArrayList<Recipe> getRecipes() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<Recipe> recipes = new ArrayList<>();

        // Fill the ArrayList with the recipes

        return recipes;
    }

    /**
     * Gets all names of Mealtypes from the database
     * @return An ArrayList<String> with the names of all Mealtypes
     */
    public ArrayList<String> getMealTypes() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/test/api/mealtype.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Maaltijdsoorten konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<String> mealtypes = new ArrayList<>();

        // Fill the ArrayList with the mealtypes

        return mealtypes;
    }

    /**
     * Gets all Countries from the database
     * @return An ArrayList<Country> with all Countries
     */
    public ArrayList<Country> getCountries() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/test/api/country.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Landen konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<Country> countries = new ArrayList<>();

        // Fill the ArrayList with the countries

        return countries;


    }

    /**
     * Gets all names of Religions from the database
     * @return An ArrayList<String> with the names of all Religions
     */
    public ArrayList<Religion> getReligions() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Religies konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<Religion> religions = new ArrayList<>();

        // Fill the ArrayList with the religions

        return religions;
    }

    /**
     * Gets all names of Dayparts (Tijdvakken) from the database
     * @return An ArrayList<String> with the names of all Dayparts
     */
    public ArrayList<String> getDayparts() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Tijdvakken konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<String> dayparts = new ArrayList<>();

        // Fill the ArrayList with the dayparts

        return dayparts;
    }

    /**
     * Gets all Ingredients from the database
     * @return An ArrayList<Ingredient> with all Ingredients
     */
    public ArrayList<Ingredient> getAllIngredients() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Ingrediënten konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        // Fill the ArrayList with the ingredients

        return ingredients;
    }

    /**
     * Gets only the approved Ingredients from the database
     * @return An ArrayList<Ingredient> with the approved Ingredients
     */
    public ArrayList<Ingredient> getApprovedIngredients() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Ingrediënten konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        // Fill the ArrayList with the ingredients

        return ingredients;
    }

    /**
     * Gets all approved Ingredients + unapproved Ingredients submitted by the Current User from the database
     * @return An ArrayList<Ingredient> with all approved Ingredients + unapproved Ingredients submitted by the Current User
     */
    public ArrayList<Ingredient> getIngredientsForSpecificUser(String username) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Ingrediënten konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        // Fill the ArrayList with the ingredients

        return ingredients;
    }

    public void setView(View view) {
        this.view = view;
    }
}
