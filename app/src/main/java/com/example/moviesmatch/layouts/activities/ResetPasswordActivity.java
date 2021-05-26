package com.example.moviesmatch.layouts.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.ActivityLoginBinding;
import com.example.moviesmatch.databinding.ActivityResetPasswordBinding;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.validation.InputsValidation;
import com.example.moviesmatch.validation.JSONManipulator;

import org.json.JSONObject;

public class ResetPasswordActivity extends AppCompatActivity {


    private ActivityResetPasswordBinding binding;
    private EditText editTextEmail;
    private InputsValidation validation;
    private CertificateByPass certificat;
    private GetRequest getRequest;
    private String URL ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUp();
    }

    private void setUp() {
        editTextEmail = binding.editTextEmail;
        validation = new InputsValidation(this);
        getRequest = new GetRequest(this);
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();

    }

    public void resetPassword(View view) {
        URL = "/api/user/sendResetPasswordEmail";
        if(validation.validateEmail(editTextEmail.getText().toString())){
            URL += "?email="+editTextEmail.getText().toString();
            getRequest.getRequest(URL, null, new IRequestCallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    new AlertDialog.Builder(getBaseContext()).setTitle("Your password has been reset.").setMessage("Please check your emails for more informations.").show();
                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                }
            });
        }

    }

}