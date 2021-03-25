package com.example.leisureapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.interfaces.VolleyCallback;
import com.example.leisureapp.models.ItemModel;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

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
