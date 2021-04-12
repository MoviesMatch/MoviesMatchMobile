package com.example.moviesmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.jar.Attributes;

public class GroupsActivity extends AppCompatActivity {

    private ListView listViewGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        //TEST
        listViewGroups = findViewById(R.id.listViewGroups);
        ArrayList arrayListGroups = new ArrayList<>(Arrays.asList("GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3"));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayListGroups);
        listViewGroups.setAdapter(arrayAdapter);
        //
    }
}