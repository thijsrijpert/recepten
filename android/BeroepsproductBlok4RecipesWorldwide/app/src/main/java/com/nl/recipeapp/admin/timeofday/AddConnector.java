package com.nl.recipeapp.admin.timeofday;

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

import java.util.ArrayList;

public class AddConnector {
    private Context context;
    private boolean success;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     */
    public AddConnector(Context context) {
        this.context = context;
    }

    /**
     * Adds a TimeOfDay to the database
     */
    public boolean addTimeOfDay(String tijdvakName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/timeofday.php?name="+ tijdvakName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                success = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                success = false;
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Dit tijdvak bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "AddTijdvak_WebserverConnector: Het tijdvak kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());

            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        return success;
    }

    /**
     * Adds a TimeOfDay to the database
     */
    public boolean editTimeOfDay(String oldName, String newName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/timeofday.php?set=name-" + newName + "&where=name-eq-" + oldName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                success = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                success = false;
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Dit tijdvak bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "AddTijdvak_WebserverConnector: Het tijdvak kon niet worden gewijzigd.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());

            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        return success;
    }
}
