package com.nl.recipeapp.review;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nl.recipeapp.RequestQueueHolder;
import com.nl.recipeapp.model.Review;

public class AddConnector {
    private Context context;
    private Add addReview;

    /**
     * RecipeHTTP Constructor
     * @param context Context of the MainActivity
     */
    public AddConnector(Context context) {
        this.context = context;
    }


    public void addReview(Review review) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://beroepsproduct.rijpert-webdesign.nl/api/review.php?title=" + review.getTitle() + "&description=" + review.getDescription() + "&rating=" + review.getRating() + "&recipeId=" + review.getRecipe_id() + "&username=" + review.getUsername(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Uw review is geplaatst", Toast.LENGTH_SHORT).show();
                addReview.onBackPressed(); // Go back to the recipe if the review is successfully placed.
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "recipeapp.review.AddConnector: De review kon niet worden goedgekeurd.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the queue and give a request
        RequestQueueHolder.getRequestQueueHolder(context).getQueue().add(stringRequest);
    }

    public void setAddReview(Add addReview) {
        this.addReview = addReview;
    }
}
