package com.nl.recipeapp.admin.mealtype;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nl.recipeapp.RequestQueueHolder;

public class AddConnector {
    private Context context;
    private Add addMealtype;
    private Edit editMealtype;

    public AddConnector(Context context) {
        this.context = context;
    }

    public void addMealtype(final String name) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Mealtype.php?name="+ name + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Maaltijdsoort '" + name + "' succesvol toegevoegd.", Toast.LENGTH_SHORT).show();
                addMealtype.getEdittext_name().setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Deze maaltijdsoort bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "admin.mealtype.AddConnector: De maaltijdsoort kon niet worden toegevoegd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    public void editMealtype(final String oldName, final String newName) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/Mealtype.php?set=name-"+ newName + "&where=name-eq-" + oldName + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Maaltijdsoort '" + oldName + "' succesvol gewijzigd naar '" + newName + "'", Toast.LENGTH_SHORT).show();
                editMealtype.getEdittext_name().setText("");
                editMealtype.initializeArrayLists();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(context, "Deze maaltijdsoort bestaat al.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "admin.mealtype.AddConnector: De maaltijdsoort kon niet worden gewijzigd.", Toast.LENGTH_SHORT).show();

            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    public void setEditMealtype(Edit editMealtype) {
        this.editMealtype = editMealtype;
    }

    public void setAddMealtype(Add addMealtype) {
        this.addMealtype = addMealtype;
    }
}
