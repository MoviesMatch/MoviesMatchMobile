package com.example.moviesmatch.requests;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesmatch.certificate.ValidateCertificate;
import com.example.moviesmatch.interfaces.IPutActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.models.MoviesMatchURLS;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class PutRequest {
    private final String API = MoviesMatchURLS.moviesMatchURL;
    private RequestQueue queue;
    private WeakReference<AppCompatActivity> weakReference;
    private JsonObjectRequest jsonObjectRequest;
    private ValidateCertificate validateCertificate;
    protected AppCompatActivity activity;

    public PutRequest(AppCompatActivity activity) {
        validateCertificate = new ValidateCertificate();
        validateCertificate.validateCertificate();
        weakReference = new WeakReference<AppCompatActivity>(activity);
        this.activity = weakReference.get();
        queue = Volley.newRequestQueue(activity);
        queue.start();
    }

    public void putRequest(JSONObject jsonObject, String url, String token, IRequestCallback requestCallback) {
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String json = new String(
                            response.data,
                            "UTF-8"
                    );

                    if (json.length() == 0) {
                        return Response.success(
                                null,
                                HttpHeaderParser.parseCacheHeaders(response)
                        );
                    } else {
                        return super.parseNetworkResponse(response);
                    }
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        queue.add(jsonObjectRequest);
    }
}

