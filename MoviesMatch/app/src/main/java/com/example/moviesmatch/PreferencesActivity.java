package com.example.moviesmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;


public class PreferencesActivity extends AppCompatActivity {

    private ListView listViewPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        listViewPreference = findViewById(R.id.listPreferences);
        ArrayList arrayListGenres = new ArrayList<>(Arrays.asList("Action", "Adventure", "Horror", "Comedy", "Thriller", "Science Fiction","Comedy", "Thriller", "Science Fiction"));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, arrayListGenres);
        listViewPreference.setAdapter(arrayAdapter);
        listViewPreference.setChoiceMode(listViewPreference.CHOICE_MODE_MULTIPLE);

        listViewPreference.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listViewPreference.getCheckedItemCount() == 5 ){
                    listViewPreference.setChoiceMode(listViewPreference.CHOICE_MODE_NONE);
                }
            }
        });

        listViewPreference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String items = (String) parent.getItemAtPosition(position);
                Toast.makeText(PreferencesActivity.this, "you selected " + items, Toast.LENGTH_SHORT).show();
                System.out.println(items);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}