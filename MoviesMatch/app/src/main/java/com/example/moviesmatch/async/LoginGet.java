package com.example.moviesmatch.async;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesmatch.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class LoginGet {

    String url = "https://my.api.mockaroo.com/users.json?key=86d5d270";
    private WeakReference<LoginActivity> weakReference;
    protected LoginActivity loginActivity;
    private RequestQueue queue;


    public LoginGet(LoginActivity loginActivity) {
        weakReference = new WeakReference<LoginActivity>(loginActivity);
        this.loginActivity = weakReference.get();
        queue = Volley.newRequestQueue(loginActivity);
        queue.start();
        getUsers();
    }

    public void getUsers() {
        System.out.println("LOOK AT THIS ");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Users");
                    loginActivity.setJsonArray(jsonArray);
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
        queue.add(request);
    }

}
