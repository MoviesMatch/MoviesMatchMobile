package com.example.moviesmatch.layouts.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
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
import com.example.moviesmatch.validation.Loading;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import pl.droidsonroids.gif.GifImageView;

public class ResetPasswordActivity extends AppCompatActivity {


    private ActivityResetPasswordBinding binding;
    private EditText editTextEmail;
    private InputsValidation validation;
    private CertificateByPass certificat;
    private GetRequest getRequest;
    private GifImageView loadingGif;
    private Loading loading;
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
        loading = new Loading();
        loadingGif = binding.resetPswLoadingGif;
        getRequest = new GetRequest(this);
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();
    }


    public void resetPassword(View view) {
        loading.loadingVisible(loadingGif);
        URL = "/api/user/sendResetPasswordEmail?email=";
        if(validation.validateEmail(editTextEmail.getText().toString())){
            URL += editTextEmail.getText().toString();
            System.out.println(URL);
            getRequest.getRequest(URL, null, new IRequestCallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    loading.loadingGone(loadingGif);
                    new AlertDialog.Builder(ResetPasswordActivity.this).setTitle("Your password has been reset.").setMessage("Please check your emails for more informations.").show().setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        }
                    });
                }
            });
        }
    }
}