package com.example.moviesmatch.async;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IPostCallback;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class PostRequest {
    private final String API = "https://10.0.2.2:44394";
    private RequestQueue queue;
    private WeakReference<AppCompatActivity> weakReference;
    private JsonObjectRequest jsonObjectRequest;
    protected AppCompatActivity activity;

    public PostRequest(AppCompatActivity activity) {
        weakReference = new WeakReference<AppCompatActivity>(activity);
        this.activity = weakReference.get();
        queue = Volley.newRequestQueue(activity);
        queue.start();
    }

    public void postRequest(JSONObject jsonObject, String url, IPostCallback postCallback) {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API + url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        postCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    System.out.println("Error " + error);
                    System.out.println("Status Code " + error.networkResponse.statusCode);
                    System.out.println("Response Data " + error.networkResponse.data);
                    System.out.println("Cause " + error.getCause());
                    System.out.println("message" + error.getMessage());
                    if (activity instanceof IPostActivity){
                        ((IPostActivity) activity).onErrorResponseAlert(error.networkResponse.statusCode);
                    }
                } catch (Exception e){
                    System.out.println(e);
                    new AlertDialog.Builder(activity).setTitle("Error").setMessage("Please try again").show();
                }
            }
        });
        queue.add(jsonObjectRequest);
    }
}

