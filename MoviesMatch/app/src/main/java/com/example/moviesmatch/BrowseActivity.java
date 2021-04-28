package com.example.moviesmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BrowseActivity extends AppCompatActivity {
    Button login, signup, groups,prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        login = (Button) findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.buttonSignup);
        groups = findViewById(R.id.buttonGroups);
        prefs = findViewById(R.id.buttonPrefs);

        login();
        signup();
        groups();
        prefs();
    }

    private void prefs() {
        prefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrowseActivity.this, GenresActivity.class));
            }
        });
    }

    private void login(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrowseActivity.this, LoginActivity.class));
            }
        });
    }

    private void signup(){
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrowseActivity.this, SignUpActivity.class));
            }
        });
    }

    private void groups(){
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrowseActivity.this, MainActivity.class));
            }
        });
    }
}