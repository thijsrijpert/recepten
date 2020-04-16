package com.nl.beroepsproductblok4_recipesworldwide.recipe;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nl.beroepsproductblok4_recipesworldwide.RequestQueueHolder;

public class RecipeHTTP {
    private Context context;

    public RecipeHTTP(Context context) {
        this.context = context;
    }

    public void addRecipe() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.google.com", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(context, "RecipeHTTP: Werkt", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Werkt niet", Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }
}
