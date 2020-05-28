package com.nl.recipeapp.admin.wordfilter;

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
    private boolean succesfullyAddedWord;
    private EditText edittext_wordName;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     * @param view The View of the Fragment that created this class. This way, objects on that Fragment can be accessed, such as EditTexts, Spinners etc.
     */
    public AddConnector(Context context, View view) {
        this.context = context;
        edittext_wordName = view.findViewById(R.id.addWord_edittext_wordName);
    }

    /**
     * Adds a word to the database
     */
    public boolean addWordfilter(String wordName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Wordfilter.php?word="+ wordName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Woord '" + edittext_wordName.getText() + "' succesvol toegevoegd.", Toast.LENGTH_SHORT).show();
//              System.out.println(response);
                succesfullyAddedWord = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                succesfullyAddedWord = false;

                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Woord '" + edittext_wordName.getText() + "' bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "AddWordfilter_WebserverConnector: Het woord '" + edittext_wordName.getText() + "' kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());

            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedWord;
    }

    /**
     * Gets all Ingredients from the database
     * @return An ArrayList<Ingredient> with all Ingredients
     */
    public ArrayList<String> getAllWordfilterValues() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "AddWordfilter_WebserverConnector: Wordfilter kon niet worden opgehaald uit de database.", Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);

        ArrayList<String> arraylist_wordfilter = new ArrayList<>();

        // Fill the ArrayList with the arraylist_wordfilter

        return arraylist_wordfilter;
    }
}
