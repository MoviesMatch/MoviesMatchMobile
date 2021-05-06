package com.example.moviesmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.moviesmatch.async.PostRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.validation.InputsValidation;


import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements IPostActivity {

    EditText editTextEmail;
    EditText editTextPsw;
    PostRequest postRequest;
    JSONObject jsonObject;
    InputsValidation validation;
    CertificateByPass certificat;

    final static String URL = "/api/user/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();
    }


    public void login(View view) {

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
        }
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        new AlertDialog.Builder(this).setTitle("Your email or password are wrong").setMessage("Please change your email or password").show();
    }

    public void setUp() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPsw = findViewById(R.id.editTextPsw);
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
        i.putExtra("Account",jsonObject.toString());
        startActivity(i);
    }
    @Override
    public void onBackPressed(){
        
    }

}