package com.example.leisureapp.interfaces;

import org.json.JSONException;

public interface VolleyCallback {
    void onSuccessResponse(String result) throws JSONException;
    void onError(String result) throws Exception;
}
