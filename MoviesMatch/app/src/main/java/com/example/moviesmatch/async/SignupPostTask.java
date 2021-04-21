package com.example.moviesmatch.async;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesmatch.SignUpActivity;
import com.example.moviesmatch.interfaces.PostCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class SignupPostTask {
    private final String URL = "https://my.api.mockaroo.com/Post/users.json?key=86d5d270&__method=POST";
    protected JSONArray responseFromURL;
    private RequestQueue queue;
    private WeakReference<SignUpActivity> weakReference;
    private JsonObjectRequest jsonObjectRequest;
    protected SignUpActivity signUpActivity;

    public SignupPostTask(SignUpActivity signUpActivity) {
        weakReference = new WeakReference<SignUpActivity>(signUpActivity);
        this.signUpActivity = weakReference.get();
        queue = Volley.newRequestQueue(signUpActivity);
        queue.start();
    }

    public void postRequest(JSONObject jsonObject, PostCallback postCallback) {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            responseFromURL = response.getJSONArray("Users");
                            postCallback.onSuccess(responseFromURL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
            }
        });
        queue.add(jsonObjectRequest);
    }
}

