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

public class AddTimeOfDay_WebserverConnector {
    private Context context;
    private boolean succesfullyAddedTijdvak;
    private EditText edittext_tijdvakName;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     * @param view The View of the Fragment that created this class. This way, objects on that Fragment can be accessed, such as EditTexts, Spinners etc.
     */
    public AddTimeOfDay_WebserverConnector(Context context, View view) {
        this.context = context;
        edittext_tijdvakName = view.findViewById(R.id.addTijdvak_edittext_tijdvakName);
    }

    /**
     * Adds a TimeOfDay to the database
     */
    public boolean addTimeOfDay(String tijdvakName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Tijdvak '" + edittext_tijdvakName.getText() + "' succesvol toegevoegd.", Toast.LENGTH_SHORT).show();
//              System.out.println(response);
                succesfullyAddedTijdvak = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "AddTijdvak_WebserverConnector: Het tijdvak '" + edittext_tijdvakName.getText() + "' kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();
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
    public ArrayList<String> getAllTimeOfDayValues() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "AddTijdvak_WebserverConnector: Tijdvakken konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<String> arraylist_timeOfDay = new ArrayList<>();

        // Fill the ArrayList with the arraylist_timeOfDay

        return arraylist_timeOfDay;
    }
}
