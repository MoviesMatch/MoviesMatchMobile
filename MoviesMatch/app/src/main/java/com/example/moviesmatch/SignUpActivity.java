package com.example.moviesmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesmatch.async.SignupPostTask;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.interfaces.PostCallback;
import com.example.moviesmatch.validation.CountryAbbreviation;
import com.example.moviesmatch.validation.InputsValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    SignupPostTask signupPostTask;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        certificateByPass = new CertificateByPass();
        certificateByPass.IngoreCertificate();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signupPostTask = new SignupPostTask(this);
        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextEmail);
        country = findViewById(R.id.spinnerCountry);
        password = findViewById(R.id.editTextPassword);
        confirmedPassword = findViewById(R.id.editTextConfirmPassword);
        jsonAccount = new JSONObject();
        inputsValidation = new InputsValidation(this);
        countryAbbreviation = new CountryAbbreviation();
    }

    public void next(View view) {
        try {
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
            signupPostTask.postRequest(jsonAccount, new PostCallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    System.out.println("START ACTIVITY" + jsonObject);
                    //Check if response contains email already taken error
                    startActivity(new Intent(SignUpActivity.this, GenresActivity.class));
                }
            });
        }
    }
}