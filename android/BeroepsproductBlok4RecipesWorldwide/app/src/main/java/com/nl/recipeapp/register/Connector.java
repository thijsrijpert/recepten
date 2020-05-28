package com.nl.recipeapp.register;

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

public class Connector {

    private Context context;
    private boolean succesfullyAddedUsername;
    private EditText edit_login, edit_password;

    /**
     * RecipeHTTP Constructor
     *
     * @param context Context of the MainActivity
     * @param view    The View of the Fragment that created this class. This way, objects on that Fragment can be accessed, such as EditTexts, Spinners etc.
     */
    public Connector(Context context, View view) {
        this.context = context;
        edit_login = view.findViewById(R.id.edit_login);
        edit_password = view.findViewById(R.id.edit_password);

    }

    /**
     * Adds an username to the database
     */
    public boolean addUsername() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/user.php?username=" + edit_login.getText().toString() + "&password=" + edit_password.getText().toString() + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Username '" + edit_login.getText().toString() + " Prima gebruikersnaam", Toast.LENGTH_SHORT).show();
                succesfullyAddedUsername = true;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "RecipeHTTP: Data duplicaat OF leeg. Error: 400", Toast.LENGTH_SHORT).show();
                } else {

                }
                Toast.makeText(context, "RecipeHTTP: Het gebruikernaam is niet juist '" + edit_login.getText().toString() + "' kon niet worden geregistreerd.", Toast.LENGTH_SHORT).show();
                succesfullyAddedUsername = false;
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
        return succesfullyAddedUsername;
    }
}