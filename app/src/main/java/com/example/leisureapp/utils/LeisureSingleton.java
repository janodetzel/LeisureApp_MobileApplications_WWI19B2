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

    public void fetchActivity(final VolleyCallback callback, TextView noInternetConnectionText) {
        SharedPreferences sharedPref = ((Activity) ctx).getPreferences(Context.MODE_PRIVATE);

        String baseURL = "https://www.boredapi.com/api/activity";
        String minPrice = "?minprice=" + SharedPreferencesHelper.getDouble(sharedPref, R.id.seekBarCosts + "filterCostsMin", 0);
        String maxPrice = "&maxprice=" + SharedPreferencesHelper.getDouble(sharedPref, R.id.seekBarCosts + "filterCostsMax", 1);
        String participants = "&participants=" + sharedPref.getInt(R.id.seekBarPersons + "filterPersons", 1);
        String type = "&type=" + sharedPref.getString(R.id.settingsTypeDropDown + "filterTypeValue", "");

        JsonObjectRequest boredAPIRequest = new JsonObjectRequest(
                Request.Method.GET,
                baseURL + minPrice + maxPrice + participants + type,
                null,
                response -> {

                    try {
                        if (response.toString().contains("error")) {
                            callback.onError(response.toString());
                        } else {
                            callback.onSuccessResponse(response.toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.e("Error Response", error.toString());
            noInternetConnectionText.setVisibility(View.VISIBLE);
        });
        this.addToRequestQueue(boredAPIRequest);
    }

    public void fetchImageURL(final VolleyCallback callback, String boredApiResponse) {
        ItemModel itemModel = new Gson().fromJson(boredApiResponse, ItemModel.class);

        String baseURL = "https://api.unsplash.com";
        String searchString = "/search/photos";
        String page = "?page=" + 1;
        String pageItems = "&per_page=" + 1;
        String orientation = "&orientation=" + "portrait";
        String searchQuery = "&query=" + itemModel.getActivity();

        String unsplashAuth = "&client_id=cOMcQHsoAAQBMhZUAR-2zZRQyNBb0lvufuME78DiDdc";

        String fullRequestString = baseURL + searchString + page + pageItems +orientation + searchQuery + unsplashAuth;

        JsonObjectRequest unsplashRequest = new JsonObjectRequest(
                Request.Method.GET,
                fullRequestString,
                null,
                response -> {
                    String imgUrl = JsonParser.parseString(response.toString())
                            .getAsJsonObject()
                            .get("results")
                            .getAsJsonArray()
                            .get(0)
                            .getAsJsonObject()
                            .get("urls")
                            .getAsJsonObject()
                            .get("small")
                            .getAsString();

                    try {
                        if (!imgUrl.equals("")) {
                            callback.onSuccessResponse(imgUrl);
                        } else {
                            callback.onError(imgUrl);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {

            try {
                callback.onError(ctx.getString(R.string.unsplash_error_replace_url));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        this.addToRequestQueue(unsplashRequest);
    }
}
