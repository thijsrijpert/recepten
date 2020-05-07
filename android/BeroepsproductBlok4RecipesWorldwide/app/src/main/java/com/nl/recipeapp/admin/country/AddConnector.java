package com.nl.recipeapp.admin.country;

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

import java.util.ArrayList;

public class AddConnector {
    private Context context;
    private boolean succesfullyAddedCountry;
    private EditText edittext_countryName;

    public AddConnector(Context context, View view) {
        this.context = context;
        edittext_countryName = view.findViewById(R.id.addCountry_edittext_countryName);
    }

    /**
     * Adds a Country to the database
     */
    public boolean addCountry(String countryCode, String countryName, String countryDescription) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/country.php?countrycode="+ countryCode + "&name=" + countryName + "&description=" + countryDescription + "", new Response.Listener<String>() {
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

    /**
     * Gets all Countries from the database
     * @return An ArrayList<Country> with all Countries
     */
    public ArrayList<Country> getCountries() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
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
}
