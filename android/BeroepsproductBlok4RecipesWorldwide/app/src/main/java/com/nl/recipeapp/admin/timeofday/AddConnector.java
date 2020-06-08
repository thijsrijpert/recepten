package com.nl.recipeapp.admin.timeofday;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nl.recipeapp.RequestQueueHolder;

public class AddConnector {
    private Context context;
    private Edit editTimeOfDay;
    private Add addTimeOfDay;

    /**
     * RecipeHTTP Constructor
     * @param context Context to be used
     */
    public AddConnector(Context context) {
        this.context = context;
    }

    /**
     * Adds a TimeOfDay value to the database
     */
    public void addTimeOfDay(final String newName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/timeofday.php?name="+ newName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Tijdvak '" + newName + "' succesvol toegevoegd.", Toast.LENGTH_SHORT).show();
                addTimeOfDay.getEdittext_name().setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Dit tijdvak bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "admin.timeofday.AddConnector: Het tijdvak kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());

            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    /**
     * Replaces a TimeOfDay value in the database with a new one (Can be used by Administrators only)
     */
    public void editTimeOfDay(final String oldName, final String newName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/timeofday.php?set=name-" + newName + "&where=name-eq-" + oldName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Tijdvak '" + oldName + "' succesvol gewijzigd naar '" + newName + "'", Toast.LENGTH_SHORT).show();
                editTimeOfDay.getEdittext_name().setText("");
                editTimeOfDay.initializeArrayLists();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Dit tijdvak bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "admin.timeofday.AddConnector: Het tijdvak kon niet worden gewijzigd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    public void setEditTimeOfDay(Edit editTimeOfDay) {
        this.editTimeOfDay = editTimeOfDay;
    }

    public void setAddTimeOfDay(Add addTimeOfDay) {
        this.addTimeOfDay = addTimeOfDay;
    }
}
