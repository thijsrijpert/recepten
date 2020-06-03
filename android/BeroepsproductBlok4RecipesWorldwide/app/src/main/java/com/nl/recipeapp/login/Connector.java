package com.nl.recipeapp.login;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nl.recipeapp.MainActivity;
import com.nl.recipeapp.R;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Religion;
import com.nl.recipeapp.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Connector {
    private Context context;
    private EditText edit_login, edit_password;

    private Fragment fragment;

    public Connector(Context context, View view, Fragment fragment) {
        this.context = context;
        edit_login = view.findViewById(R.id.edit_login);
        edit_password = view.findViewById(R.id.edit_password);

        this.fragment = fragment;

    }

        public void login() {
            // Request a string response from the provided URL.
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/user.php?where=username-eq-" + edit_login.getText().toString() + ".password-eq-" + edit_password.getText().toString() + "", new JSONArray(), new Response.Listener<JSONArray>() {
                public void onResponse(JSONArray response) {

                    Gson gson = new Gson();

                    JSONObject object = null;
                    try {
                        object = response.getJSONObject(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    User user = gson.fromJson(object.toString(), User.class);

                    Toast.makeText(context, "Username '" + edit_login.getText().toString() + " is nu ingelogd", Toast.LENGTH_SHORT).show();
                    user.setUsername(edit_login.getText().toString());
                    ((MainActivity)fragment.getActivity()).setCurrentUser(user);
                    ((MainActivity)fragment.getActivity()).getMain_textview_loggedInUser().setText(user.getUsername() +" is ingelogd.");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse.statusCode == 400) {
                        Toast.makeText(context, "Data duplicaat OF leeg. Error: 400", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                    Toast.makeText(context, "Het gebruikernaam of wachtwoord is niet juist '" + edit_login.getText().toString() + "' probeer op nieuw.", Toast.LENGTH_SHORT).show();

                }
            });

            // Get the queue and give a request
            RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);

        }
    }