package com.example.moviesmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.moviesmatch.async.LoginPost;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.validation.InputsValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPsw;
    LoginPost loginPost;
    InputsValidation validation;
    CertificateByPass certificat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPsw = findViewById(R.id.editTextPsw);
        loginPost = new LoginPost(this);
        validation = new InputsValidation(this);
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();
    }



    public void login(View view) {
        loginPost.postRequestLogin(editTextEmail.getText().toString(), editTextPsw.getText().toString());
    }




   /* public boolean checkLogin() {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject user = null;
            try {
                user = jsonArray.getJSONObject(i);
                String userEmail = user.getString("email");
                String userPsw = user.getString("password");
                if (editTextEmail.getText().toString().equals(userEmail) && editTextPsw.getText().toString().equals(userPsw)) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }*/

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }


    @Override
    public void onBackPressed() {
        //Do nothing
    }

    public void startAct() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}