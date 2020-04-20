package com.nl.beroepsproductblok4_recipesworldwide.administrator.country;

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

import java.util.ArrayList;

public class AddCountry_WebserverConnector {
    private Context context;
    private boolean succesfullyAddedCountry;
    private EditText edittext_countryName;

    public AddCountry_WebserverConnector(Context context, View view) {
        this.context = context;
        edittext_countryName = view.findViewById(R.id.addCountry_edittext_countryName);
    }

    /**
     * Adds a Country to the database
     */
    public boolean addCountry(String countryName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Land '" + edittext_countryName.getText() + "' succesvol toegevoegd.", Toast.LENGTH_SHORT).show();
//              System.out.println(response);
                succesfullyAddedCountry = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "AddCountry_WebserverConnector: Het land '" + edittext_countryName.getText() + "' kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
                succesfullyAddedCountry = false;
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedCountry;
    }

    public ArrayList<String> getAllCountryNames() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "AddCountry_WebserverConnector: Landnamen konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<String> countryNames = new ArrayList<>();

        // Fill the ArrayList with the countryNames

        return countryNames;
    }
}
