package com.nl.recipeapp.ingredient;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Ingredient;

import java.util.ArrayList;

public class AddConnector {
    private Context context;
    private Add addIngredient;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     */
    public AddConnector(Context context) {
            this.context = context;
    }

    /**
     * Adds an ingredient to the database
     */
    public void addIngredient(final Ingredient ingredient) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?name="+ ingredient.getName() + "&description=" + ingredient.getDescription() + "&is_approved=" + ingredient.isApproved() + "&username=" + ingredient.getUsername(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Ingrediënt '" + ingredient.getName() + "' succesvol aangemeld. Een administrator zal het beoordelen.", Toast.LENGTH_SHORT).show();
                addIngredient.getEdittexts(0).setText("");
                addIngredient.getEdittexts(1).setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "RecipeHTTP: Data duplicaat OF leeg. Error: 400", Toast.LENGTH_SHORT).show();
                } else {

                }
                Toast.makeText(context, "RecipeHTTP: Het ingrediënt '" + ingredient.getName() + "' kon niet worden aangemeld.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
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

    public void setAddIngredient(Add addIngredient) {
        this.addIngredient = addIngredient;
    }
}
