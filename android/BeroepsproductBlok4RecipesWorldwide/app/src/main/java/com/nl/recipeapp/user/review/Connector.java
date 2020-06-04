package com.nl.recipeapp.user.review;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Review;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Connector {
    private Context context;
    private boolean result;

    private Manage manageUserReview;

    // Local Arraylists
    ArrayList<Review> arraylist_reviews;

    public Connector(Context context) {
        this.context = context;

        arraylist_reviews = new ArrayList<>();
    }

    public void saveChanges(String title, String description, String rating, String id) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/review.php?set=title-" + title + ".description-" + description + ".rating-" + rating + "&where=id-eq-" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Uw review is geüpdatet", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "recipeapp.user.review.Connector: Deze review kon niet worden geüpdatet.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    public void delete(String id) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/review.php?delete=id-" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Uw review is verwijderd", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "recipeapp.user.review.Connector: Deze review kon niet worden verwijderd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    public void getReviewsForSpecificUser(String username, final String calledFrom) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/review.php?where=username-eq-" + username, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                arraylist_reviews.clear(); // Clear the local ArrayList, so no duplicates will be added

                try {
                    for (int c = 0; c < response.length(); c++) {
                        JSONObject object = response.getJSONObject(c);
                        Review review = gson.fromJson(object.toString(), Review.class);
                        arraylist_reviews.add(review);
                    }

                    // Clear the ArrayLists, so they only get filled once (and not stacked with new object on top of the old ones)
                    // Add the Reviews to the necessary ArrayLists
                    // Notify the corresponding adapters that the ArrayLists have been changed and they need to be updated
                    switch (calledFrom) {
                        case "ManageUserReview":
                            manageUserReview.getArrayList_reviews().clear();
                            manageUserReview.getArrayList_reviews().addAll(arraylist_reviews);

                            for (int c = 0; c < arraylist_reviews.size(); c++) {
                                manageUserReview.getArraylist_reviewNames().add(arraylist_reviews.get(c).getTitle());
                            }

                            manageUserReview.getArrayadapter_reviewNames().notifyDataSetChanged();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Connector (user/review): Reviews voor een specifieke gebruiker konden niet worden opgehaald uit de database.");
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(request);
    }

    public void setManageUserReview(com.nl.recipeapp.user.review.Manage manageUserReview) {
        this.manageUserReview = manageUserReview;
    }
}
