package com.example.moviesmatch.async;

import android.content.Intent;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class LoginPost {

    String url = "https://10.0.2.2:44394/api/user/login";
    private WeakReference<LoginActivity> weakReference;
    protected LoginActivity loginActivity;
    private RequestQueue queue;


    public LoginPost(LoginActivity loginActivity) {
        weakReference = new WeakReference<LoginActivity>(loginActivity);
        this.loginActivity = weakReference.get();
        queue = Volley.newRequestQueue(loginActivity);
        queue.start();
    }

    public void postRequestLogin(String email, String password) {

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("usrEmail",email);
            jsonObject.put("usrPassword",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);
                loginActivity.startAct();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
              Map<String, String>params  = new HashMap<>();
              params.put("usrEmail",email);
              params.put("usrPassword",password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
              //  headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }


        };
        queue.add(request);
    }

}
