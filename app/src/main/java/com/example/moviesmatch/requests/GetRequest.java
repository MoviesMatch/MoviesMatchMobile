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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesmatch.certificate.ValidateCertificate;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.models.MoviesMatchURLS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class GetRequest {
    private final String API = MoviesMatchURLS.moviesMatchURL;
    private RequestQueue queue;
    private WeakReference<AppCompatActivity> weakReference;
    private JsonObjectRequest jsonObjectRequest;
    private JsonArrayRequest jsonArrayRequest;
    private ValidateCertificate validateCertificate;
    protected AppCompatActivity activity;

    public GetRequest(AppCompatActivity activity) {
        validateCertificate = new ValidateCertificate();
        validateCertificate.validateCertificate();
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
                if (activity instanceof IGetActivity) {
                    try {
                        ((IGetActivity) activity).onGetErrorResponse(error.networkResponse.statusCode);
                    } catch (NullPointerException e) {
                        ((IGetActivity) activity).onGetErrorResponse(0);
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
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
                if (activity instanceof IGetActivity) {
                    try {
                        ((IGetActivity) activity).onGetErrorResponse(error.networkResponse.statusCode);
                    } catch (NullPointerException e) {
                        ((IGetActivity) activity).onGetErrorResponse(0);
                    }
                }
            }
        }) {
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
