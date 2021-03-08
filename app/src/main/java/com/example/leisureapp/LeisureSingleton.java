package com.example.leisureapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class LeisureSingleton {

    private static LeisureSingleton instance;
    private RequestQueue requestQueue;

    private static Context ctx;

    private LeisureSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized LeisureSingleton getInstance(Context context){
        if (instance == null) {
            instance = new LeisureSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
