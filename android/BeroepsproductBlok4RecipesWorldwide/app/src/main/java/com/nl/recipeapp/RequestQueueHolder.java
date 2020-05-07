package com.nl.recipeapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueHolder {
    private static RequestQueueHolder instance;
    private RequestQueue queue;

    private RequestQueueHolder(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static RequestQueueHolder getRequestQueueHolder(Context context) {

        if (instance == null) {
            instance = new RequestQueueHolder(context);
        }

        return instance;
    }

    public RequestQueue getQueue() {
        return queue;
    }
}
