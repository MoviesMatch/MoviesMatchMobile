package com.example.moviesmatch.requests;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class GetRequest {
    private final String API = "http://EQ2.420-6D9-LI.E2021.info.cegeplimoilou.ca";
    private RequestQueue queue;
    private WeakReference<AppCompatActivity> weakReference;
    private JsonObjectRequest jsonObjectRequest;
    private JsonArrayRequest jsonArrayRequest;
    protected AppCompatActivity activity;

    public GetRequest(AppCompatActivity activity) {
        weakReference = new WeakReference<AppCompatActivity>(activity);
        this.activity = weakReference.get();
        queue = Volley.newRequestQueue(activity);
        queue.start();
    }

    public void getRequest(String url, String token, IRequestCallback requestCallback) {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API + url, null,
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
                //Calls each classes onErrorResponseAlert method that implements IGetActivity to have custom error messages
                if (activity instanceof IGetActivity){
                    try{
                        ((IGetActivity) activity).onGetErrorResponse(error.networkResponse.statusCode);
                    } catch (NullPointerException e){
                        ((IGetActivity) activity).onGetErrorResponse(0);
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void getRequestArray(String url, String token, IRequestCallbackArray requestCallback) {
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API + url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
                        requestCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error " + error);
                //Calls each classes onErrorResponseAlert method that implements IGetActivity to have custom error messages
                if (activity instanceof IGetActivity){
                    try{
                        ((IGetActivity) activity).onGetErrorResponse(error.networkResponse.statusCode);
                    } catch (NullPointerException e){
                        ((IGetActivity) activity).onGetErrorResponse(0);
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
}
