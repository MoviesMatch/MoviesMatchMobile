package com.example.moviesmatch.async;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesmatch.interfaces.IPutActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class PutRequest {
    private final String API = "https://10.0.2.2:44394";
    private RequestQueue queue;
    private WeakReference<AppCompatActivity> weakReference;
    private JsonObjectRequest jsonObjectRequest;
    protected AppCompatActivity activity;

    public PutRequest(AppCompatActivity activity) {
        weakReference = new WeakReference<AppCompatActivity>(activity);
        this.activity = weakReference.get();
        queue = Volley.newRequestQueue(activity);
        queue.start();
    }

    public void putRequest(JSONObject jsonObject, String url, IRequestCallback requestCallback) {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, API + url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        requestCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error " + error);
                //Calls each classes onErrorResponseAlert method that implements IPostActivity to have custom error messages
                if (activity instanceof IPutActivity){
                    try{
                        ((IPutActivity) activity).onPutErrorResponse(error.networkResponse.statusCode);
                    } catch (NullPointerException e){
                        ((IPutActivity) activity).onPutErrorResponse(0);
                    }
                }
            }
        });
        queue.add(jsonObjectRequest);
    }
}

