package com.nl.recipeapp.admin.mealtype;

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

public class AddConnector {
    private Context context;
    private boolean succesfullyAddedMealtype;

    public AddConnector(Context context) {
        this.context = context;
    }

    public boolean addMealtype(String mealtypeName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/thijs/api/mealtype.php?name="+ mealtypeName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                succesfullyAddedMealtype = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                succesfullyAddedMealtype = false;

                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Deze maaltijdsoort bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "De maaltijdsoort kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());

            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedMealtype;

    }

    public boolean editMealtype(String oldName, String newName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/thijs/api/mealtype.php?name="+ newName + "&where=name-eq-" + oldName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                succesfullyAddedMealtype = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                succesfullyAddedMealtype = false;

                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Deze maaltijdsoort bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "De maaltijdsoort kon niet worden gewijzigd.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());

            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedMealtype;

    }
}
