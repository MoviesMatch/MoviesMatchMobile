package com.example.moviesmatch.layouts.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.moviesmatch.databinding.ActivityLoginBinding;
import com.example.moviesmatch.requests.PostRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.validation.InputsValidation;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity implements IPostActivity {

    private ActivityLoginBinding binding;
    private EditText editTextEmail;
    private EditText editTextPsw;
    private PostRequest postRequest;
    private JSONObject jsonObject;
    private InputsValidation validation;
    private CertificateByPass certificat;
    private GifImageView loadingGif;
    private Button loginButton;

    private final static String URL = "/api/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUp();
    }


    public void login(View view) {
        loading();
        try {
            jsonObject.put("usrEmail", editTextEmail.getText().toString());
            jsonObject.put("usrPassword", editTextPsw.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (validation.validateEmail(editTextEmail.getText().toString())) {
            postRequest.postRequest(jsonObject, URL, new IRequestCallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    startAct(jsonObject);
                }
            });
        } else{
            loadingGone();
        }
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        loadingGone();
        if (errorCode == 401) {
            new AlertDialog.Builder(this).setTitle("Your email or password are wrong").setMessage("Please change your email or password").show();
        } else {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
        }
    }

    public void setUp() {
        editTextEmail = binding.editTextEmail;
        editTextPsw = binding.editTextPsw;
        loadingGif = binding.loginLoadingGif;
        loginButton = binding.buttonLogin;
        jsonObject = new JSONObject();
        postRequest = new PostRequest(this);
        validation = new InputsValidation(this);
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();
    }


    public void register(View view) {
        startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
    }


    public void startAct(JSONObject jsonObject) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("Account", jsonObject.toString());
        startActivity(i);
    }

    private void loading() {
        loadingGif.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);
    }

    private void loadingGone() {
        loadingGif.setVisibility(View.GONE);
        loginButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {

    }

}