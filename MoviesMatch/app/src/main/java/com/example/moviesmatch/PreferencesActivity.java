package com.example.moviesmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;


public class PreferencesActivity extends AppCompatActivity {

    private ListView listViewPreference;
    private Button buttonClear, buttonSavePref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // code pour les bouttons
        buttonClear = findViewById(R.id.buttonClear);
        buttonSavePref = findViewById(R.id.buttonSavePref);

        // code pour la listview
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

    }

    public void savePrefs(View view){
        startActivity(new Intent(PreferencesActivity.this, MainActivity.class));
    }

    public void clearPrefs(View view){
        for(int i = 0; i < listViewPreference.getCheckedItemCount(); i++ ){
            listViewPreference.setItemChecked(i,false);
        }
        listViewPreference.setChoiceMode(listViewPreference.CHOICE_MODE_MULTIPLE);
    }


}