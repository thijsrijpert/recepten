package com.nl.recipeapp.religion;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nl.recipeapp.R;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Religion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddConnector {
    private Context context;
    private View view;
    private EditText edittext_religionName;
    private Edit editReligion;
    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     * @param view The View of the Fragment that created this class. This way, objects on that Fragment can be accessed, such as EditTexts, Spinners etc.
     */
    public AddConnector(Context context, View view) {
        this.context = context;
        edittext_religionName = view.findViewById(R.id.addReligion_edittext_religionName);
    }

    /**
     * Adds an ingredient to the database
     */
    public void addReligion(String religionName) {
        System.out.println(religionName);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/religion.php?name=" + religionName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Religie '" + edittext_religionName.getText().toString() + "' succesvol aangemeld. ", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Het religie '" + edittext_religionName.getText().toString() + "' kon niet worden aangemeld.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

    }

    public void editReligion(final String oldWord, final String newWord) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/religion.php?set=name-" + newWord + "&where=name-eq-" + oldWord + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Religie '" + oldWord + "' succesvol gewijzigd naar '" + newWord + "'", Toast.LENGTH_SHORT).show();
                editReligion.getEdittext_religion().setText("");
                editReligion.initializeArrayLists();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Deze religie bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Errorcode toevoegen die weergeven moet worden wanneer een woord te lang is

                Toast.makeText(context, "admin.wordfilter.AddConnector: Het woord '" + oldWord + "' kon niet worden gewijzigd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    /**
     * Gets all Ingredients from the database
     * @return An ArrayList<Ingredient> with all Ingredients
     */
    public ArrayList<Religion> getAllReligion() {
        // Request a JsonArray response from the provided URL.
        final ArrayList<Religion> religions = new ArrayList<>();

        // tablename.php --> selecteert alles uit de tabel
        // tablename.php?select=kolomnaam --> selecteert alles uit een specifieke kolom (kolomnaam)
        // tablename.php?select=kolomnaam&where=kolomnaam-eq-waardenaam --> selecteert alles uit een specifieke kolom, waar de waarde uit de kolom gelijk is aan de waardenaam

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/religion.php", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                System.out.println("Response: " + response.toString());
                Gson gson = new Gson();

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Religion religion = gson.fromJson(object.toString(), Religion.class);

                        religions.add(religion);

                        System.out.println(religion.getId());
                        System.out.println(religion.getName());
                    }
                    editReligion.getArraylist_religion().clear();
                    editReligion.getArraylist_religion().addAll(religions);
                    editReligion.getArrayadapter_religion().notifyDataSetChanged();
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

        return religions;
    }

    public void setEdittext_religionName(com.nl.recipeapp.religion.Edit editReligion) {
        this.editReligion = editReligion;
    }
}
