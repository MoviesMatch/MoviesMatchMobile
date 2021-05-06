package com.example.moviesmatch.layouts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moviesmatch.R;

public class BrowseActivity extends AppCompatActivity {
    Button login, signup, groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        login = (Button) findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.buttonSignup);
        groups = findViewById(R.id.buttonGroups);

        login();
        signup();
        groups();
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
                startActivity(new Intent(BrowseActivity.this, CreateAccountActivity.class));
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