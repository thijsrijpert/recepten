package com.nl.recipeapp.admin.wordfilter;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Word;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddConnector {
    private Context context;
    private Add addWord;
    private Edit editWord;
    private ArrayList<Word> arraylist_wordfilter;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     */
    public AddConnector(Context context) {
        this.context = context;
        arraylist_wordfilter = new ArrayList<>();
    }

    /**
     * Adds a word to the database
     */
    public void addWord(final String word) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Wordfilter.php?word="+ word + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Woord '" + word + "' succesvol toegevoegd.", Toast.LENGTH_SHORT).show();
                addWord.getEdittext_word().setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Woord '" + word + "' bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Errorcode toevoegen die weergeven moet worden wanneer een woord te lang is

                Toast.makeText(context, "admin.wordfilter.AddConnector: Het woord '" + word + "' kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();

            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    /**
     * Replaces a Word value in the database with a new one (Can be used by Administrators only)
     */
    public void editWord(final String oldWord, final String newWord) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Wordfilter.php?set=word-" + newWord + "&where=word-eq-" + oldWord + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Woord '" + oldWord + "' succesvol gewijzigd naar '" + newWord + "'", Toast.LENGTH_SHORT).show();
                editWord.getEdittext_word().setText("");
                editWord.initializeArrayLists();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Dit woord bestaat al.", Toast.LENGTH_SHORT).show();
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
     * Gets all Mealtypes from the database
     */
    public void getWordfilter(final String calledFrom) {
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Wordfilter.php", new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_wordfilter.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Word word = gson.fromJson(object.toString(), Word.class);
                        arraylist_wordfilter.add(word);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Mealtypes to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "editWord":
                            editWord.getArrayList_words().clear();
                            editWord.getArrayList_words().addAll(arraylist_wordfilter);
                            editWord.getArrayAdapter_words().clear();
                            editWord.getArrayAdapter_words().addAll(arraylist_wordfilter);
                            editWord.getArrayAdapter_words().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("admin.wordfilter.AddConnector: Woordfilter kon niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    public void setEditWord(Edit editWord) {
        this.editWord = editWord;
    }

    public void setAddWord(Add addWord) {
        this.addWord = addWord;
    }
}
