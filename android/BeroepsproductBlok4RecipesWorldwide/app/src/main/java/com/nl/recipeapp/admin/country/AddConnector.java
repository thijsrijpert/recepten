package com.nl.recipeapp.admin.country;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nl.recipeapp.RequestQueueHolder;

public class AddConnector {
    private Context context;
    private Add addCountry;
    private Edit editCountry;

    public AddConnector(Context context) {
        this.context = context;
    }

    /**
     * Adds a Country to the database
     */
    public void addCountry(String code, final String name, String description) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/country.php?countrycode="+ code + "&name=" + name + "&description=" + description + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Land '" + name + "' succesvol toegevoegd.", Toast.LENGTH_SHORT).show();
                addCountry.getEdittextFields(0).setText("");
                addCountry.getEdittextFields(1).setText("");
                addCountry.getEdittextFields(2).setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "admin.country.AddConnector: Het land kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    /**
     * Edits an existing Country in the database
     */
    public void editCountry(String oldCode, final String oldName, String newCode, final String newName, String newDescription) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/country.php?set=countrycode-"+ newCode + "&name-" + newName + "&description-" + newDescription + "&where=countrycode-eq-" + oldCode + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Land '" + oldName + "' succesvol gewijzigd naar '" + newName + "'", Toast.LENGTH_SHORT).show();
                editCountry.getEdittextFields(0).setText("");
                editCountry.getEdittextFields(1).setText("");
                editCountry.getEdittextFields(2).setText("");
                editCountry.initializeArrayLists();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "admin.country.AddConnector: Het land kon niet worden gewijzigd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    public void setEditCountry(Edit editCountry) {
        this.editCountry = editCountry;
    }

    public void setAddCountry(Add addCountry) {
        this.addCountry = addCountry;
    }
}
