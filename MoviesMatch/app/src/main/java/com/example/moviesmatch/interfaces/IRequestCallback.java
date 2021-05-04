package com.example.moviesmatch.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IRequestCallback {
    void onSuccess(JSONObject jsonObject);
    void onSuccess(JSONArray jsonObject);
}
