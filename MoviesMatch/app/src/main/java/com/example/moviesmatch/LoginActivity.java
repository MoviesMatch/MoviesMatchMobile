package com.example.moviesmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public void register(View view){
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    @Override
    public void onBackPressed(){
        //Do nothing
    }
}