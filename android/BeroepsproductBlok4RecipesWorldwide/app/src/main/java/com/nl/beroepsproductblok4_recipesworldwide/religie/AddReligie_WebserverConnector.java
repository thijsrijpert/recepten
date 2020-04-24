package com.nl.beroepsproductblok4_recipesworldwide.religie;

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

public class AddReligie_WebserverConnector {
    private Context context;
    private boolean succesfullyAddedReligie;
    private EditText edittext_religieName;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     * @param view The View of the Fragment that created this class. This way, objects on that Fragment can be accessed, such as EditTexts, Spinners etc.
     */
    public AddReligie_WebserverConnector(Context context, View view) {
        this.context = context;
        edittext_religieName = view.findViewById(R.id.addReligie_edittext_religieName);
    }

    /**
     * Adds an ingredient to the database
     */
    public boolean addReligie(String religieName) {
        System.out.println(religieName);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/test/thijs/api/religion.php?name=" + religieName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Religie '" + edittext_religieName.getText().toString() + "' succesvol aangemeld. ", Toast.LENGTH_SHORT).show();
//              System.out.println(response);
                succesfullyAddedReligie = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Het religie '" + edittext_religieName.getText().toString() + "' kon niet worden aangemeld.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
                succesfullyAddedReligie = false;
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedReligie;
    }
    /**
     * Gets all Ingredients from the database
     * @return An ArrayList<Ingredient> with all Ingredients
     */
    public ArrayList<String> getAllReligie() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RecipeHTTP: Religie konden niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<String> reliegies = new ArrayList<>();

        // Fill the ArrayList with the religions

        return reliegies;
    }
}
