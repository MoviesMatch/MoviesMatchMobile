package com.example.moviesmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moviesmatch.async.PostRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IPostCallback;
import com.example.moviesmatch.validation.CountryAbbreviation;
import com.example.moviesmatch.validation.InputsValidation;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

public class SignUpActivity extends AppCompatActivity implements IPostActivity {
    PostRequest postRequest;
    EditText firstName;
    EditText lastName;
    EditText email;
    Spinner country;
    EditText password;
    EditText confirmedPassword;
    JSONObject jsonAccount;
    InputsValidation inputsValidation;
    CountryAbbreviation countryAbbreviation;
    CertificateByPass certificateByPass;
    GifImageView loadingGif;
    Button nextButton;

    private final String URL = "/api/user/signUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setup();
    }

    public void next(View view) {
        try {
            loadingGif.setVisibility(View.VISIBLE);
            nextButton.setEnabled(false);
            jsonAccount.put("usrEmail", email.getText().toString());
            jsonAccount.put("usrFirstname", firstName.getText().toString());
            jsonAccount.put("usrLastname", lastName.getText().toString());
            jsonAccount.put("usrPassword", password.getText().toString());
            jsonAccount.put("usrCountry", countryAbbreviation.getCountryAbbreviation(country.getSelectedItem().toString()));
            System.out.println(jsonAccount);
            createAccount();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createAccount() {
        if (inputsValidation.validateName(firstName.getText().toString(), lastName.getText().toString())
                && inputsValidation.validateEmail(email.getText().toString())
                && inputsValidation.validatePassword(password.getText().toString(), confirmedPassword.getText().toString())) {

            postRequest.postRequest(jsonAccount, URL, new IPostCallback() {
                //Called when postRequest is done
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    Intent intent = new Intent(SignUpActivity.this, GenresActivity.class);
                    intent.putExtra("Account", jsonObject.toString());
                    startActivity(intent);
                    loadingGif.setVisibility(View.GONE);
                    nextButton.setEnabled(true);
                }
            });
        } else {
            loadingGif.setVisibility(View.GONE);
            nextButton.setEnabled(true);
        }
    }

    @Override
    public void onErrorResponseAlert(int errorCode) {
        loadingGif.setVisibility(View.GONE);
        nextButton.setEnabled(true);
        if (errorCode == 403){
            new AlertDialog.Builder(this).setTitle("Error").setMessage("This email is already taken").show();
        } else {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Please try again").show();
        }
    }

    private void setup(){
        certificateByPass = new CertificateByPass();
        certificateByPass.IngoreCertificate();
        postRequest = new PostRequest(this);
        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextEmail);
        country = findViewById(R.id.spinnerCountry);
        password = findViewById(R.id.editTextPassword);
        confirmedPassword = findViewById(R.id.editTextConfirmPassword);
        jsonAccount = new JSONObject();
        inputsValidation = new InputsValidation(this);
        countryAbbreviation = new CountryAbbreviation();
        loadingGif = findViewById(R.id.signUpLoadingGif);
        nextButton = findViewById(R.id.buttonRegister) ;
    }
}