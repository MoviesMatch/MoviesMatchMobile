package com.example.moviesmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BrowseActivity extends AppCompatActivity {
    Button login, signup, groups, swipe, account, settings, match, createGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        login = (Button) findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.buttonSignup);
        groups = findViewById(R.id.buttonGroups);
        swipe = findViewById(R.id.buttonSwipe);
        account = findViewById(R.id.buttonAccount);
        settings = findViewById(R.id.buttonSettings);
        match = findViewById(R.id.buttonMatch);
        createGroup = findViewById(R.id.buttonCreateGroup);

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