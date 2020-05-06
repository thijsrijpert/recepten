package com.nl.beroepsproductblok4_recipesworldwide.religie;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nl.beroepsproductblok4_recipesworldwide.R;
import com.nl.beroepsproductblok4_recipesworldwide.RequestQueueHolder;
import com.nl.beroepsproductblok4_recipesworldwide.model.Religion;

import org.json.JSONArray;
import org.json.JSONObject;

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
    public ArrayList<Religion> getAllReligie() {
        // Request a JsonArray response from the provided URL.
        final ArrayList<Religion> reliegies = new ArrayList<>();

        // tablename.php --> selecteert alles uit de tabel
        // tablename.php?select=kolomnaam --> selecteert alles uit een specifieke kolom (kolomnaam)
        // tablename.php?select=kolomnaam&where=kolomnaam-eq-waardenaam --> selecteert alles uit een specifieke kolom, waar de waarde uit de kolom gelijk is aan de waardenaam

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/test/api/religion.php", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                System.out.println("Response: " + response.toString());
                Gson gson = new Gson();

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Religion religion = gson.fromJson(object.toString(), Religion.class);

                        reliegies.add(religion);

                        System.out.println(religion.getId());
                        System.out.println(religion.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: " + error.networkResponse.headers.toString());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);

        // Fill the ArrayList with the religions

        return reliegies;
    }
}
