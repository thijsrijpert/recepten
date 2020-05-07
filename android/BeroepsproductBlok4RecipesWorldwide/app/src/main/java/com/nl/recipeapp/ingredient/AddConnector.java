package com.nl.recipeapp.ingredient;

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
import com.nl.recipeapp.model.Ingredient;

import java.util.ArrayList;

public class AddConnector {
    private Context context;
    private boolean succesfullyAddedIngredient;
    private EditText edittext_ingredientName;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     * @param view The View of the Fragment that created this class. This way, objects on that Fragment can be accessed, such as EditTexts, Spinners etc.
     */
    public AddConnector(Context context, View view) {
        this.context = context;
        edittext_ingredientName = view.findViewById(R.id.addIngredient_edittext_ingredientName);
    }

    /**
     * Adds an ingredient to the database
     */
    public boolean addIngredient(Ingredient ingredient) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/ingredient.php?name="+ ingredient.getName() + "&description=" + ingredient.getDescription() + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Ingrediënt '" + edittext_ingredientName.getText() + "' succesvol aangemeld. Een administrator zal het beoordelen.", Toast.LENGTH_SHORT).show();
//              System.out.println(response);
                succesfullyAddedIngredient = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "RecipeHTTP: Data duplicaat OF leeg. Error: 400", Toast.LENGTH_SHORT).show();
                } else {

                }
                Toast.makeText(context, "RecipeHTTP: Het ingrediënt '" + edittext_ingredientName.getText() + "' kon niet worden aangemeld.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
                succesfullyAddedIngredient = false;
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedIngredient;
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
}
