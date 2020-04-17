package com.nl.beroepsproductblok4_recipesworldwide.administrator.tijdvakken;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nl.beroepsproductblok4_recipesworldwide.R;
import com.nl.beroepsproductblok4_recipesworldwide.RequestQueueHolder;
import com.nl.beroepsproductblok4_recipesworldwide.model.Ingredient;

import java.util.ArrayList;

public class AddTijdvak_WebserverConnector {
    private Context context;
    private boolean succesfullyAddedTijdvak;
    private EditText edittext_tijdvakName;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     * @param view The View of the Fragment that created this class. This way, objects on that Fragment can be accessed, such as EditTexts, Spinners etc.
     */
    public AddTijdvak_WebserverConnector(Context context, View view) {
        this.context = context;
        edittext_tijdvakName = view.findViewById(R.id.addTijdvak_edittext_tijdvakName);
    }

    /**
     * Adds an ingredient to the database
     */
    public boolean addTijdvak(String tijdvakName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Tijdvak '" + edittext_tijdvakName.getText() + "' succesvol aangemeld. Een administrator zal het beoordelen.", Toast.LENGTH_SHORT).show();
//              System.out.println(response);
                succesfullyAddedTijdvak = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Het tijdvak '" + edittext_tijdvakName.getText() + "' kon niet worden aangemeld.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
                succesfullyAddedTijdvak = false;
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedTijdvak;
    }

    /**
     * Gets all Ingredients from the database
     * @return An ArrayList<Ingredient> with all Ingredients
     */
    public ArrayList<String> getAllTijdvakken() {
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

        ArrayList<String> tijdvakken = new ArrayList<>();

        // Fill the ArrayList with the tijdvakken

        return tijdvakken;
    }
}
