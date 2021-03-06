package com.example.moviesmatch.layouts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.moviesmatch.R;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    TextView moviesMatch;
    Animation animMoviesMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash);
        moviesMatch = findViewById(R.id.moviesMatch);
        animation();


        //TEST
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        },2000);
    }

    private void animation(){
        animMoviesMatch = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        moviesMatch.startAnimation(animMoviesMatch);
    }
}