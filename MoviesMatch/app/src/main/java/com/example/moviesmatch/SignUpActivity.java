package com.example.moviesmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moviesmatch.async.SignupPostTask;
import com.example.moviesmatch.interfaces.PostCallback;
import com.example.moviesmatch.validation.AccountValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    SignupPostTask signupPostTask;
    EditText firstName;
    EditText lastName;
    EditText email;
    Spinner country;
    EditText password;
    EditText confirmedPassword;
    JSONObject jsonAccount;
    AccountValidation accountValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
        accountValidation = new AccountValidation(this);
    }

    public void next(View view) {
        try {
            jsonAccount.put("FirstName", firstName.getText().toString());
            jsonAccount.put("LastName", lastName.getText().toString());
            jsonAccount.put("Email", email.getText().toString());
            jsonAccount.put("Country", country.getSelectedItem().toString());
            jsonAccount.put("Password", password.getText().toString());
            createAccount();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createAccount() {
        if (accountValidation.validateName(firstName.getText().toString(), lastName.getText().toString())
                && accountValidation.validateEmail(email.getText().toString())
                && accountValidation.validatePassword(password.getText().toString(), confirmedPassword.getText().toString())) {
            signupPostTask.postRequest(jsonAccount, new PostCallback() {
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    System.out.println("START ACTIVITY" + jsonArray);
                    //Check if response contains email already taken error
                    startActivity(new Intent(SignUpActivity.this, PreferencesActivity.class));
                }
            });
        }
    }
}