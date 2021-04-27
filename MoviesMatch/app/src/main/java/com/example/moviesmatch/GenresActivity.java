package com.example.moviesmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GenresActivity extends AppCompatActivity {

    private ListView listViewGenres;
    private Button  buttonSavePref;
    private ArrayList<Genre> listGenres;
    private GenresListAdapter arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        // code pour les bouttons
        buttonSavePref = findViewById(R.id.buttonSavePref);
        
        // code pour la listview
        listViewGenres = findViewById(R.id.listGenres);
        listGenres = new ArrayList<Genre>(Arrays.asList(new Genre("Action", false), new Genre("Adventure", false), new Genre("Horror", false), new Genre("Comedy", false), new Genre("Thriller", false), new Genre("Science-Fiction", false), new Genre("Romance", false)));
        arrayAdapter  = new GenresListAdapter(this, listGenres);
        listViewGenres.setAdapter(arrayAdapter);

    }

    public boolean isFiveChecked(){
        int numberChecked = 0 ;
        for(int i = 0; i < listGenres.size();i++){
            if(listGenres.get(i).isChecked()){
                numberChecked++;
            }

            if(numberChecked == 5){
              return true;
            }
        }
        return  false;
    }

    public void savePrefs(View view){
        if(isFiveChecked()){
            startActivity(new Intent(GenresActivity.this, MainActivity.class));
        }else{
            new AlertDialog.Builder(this).setTitle("You didn't check 5 genres").setMessage("Please check exactly 5 genres of movies you like").show();
        }

    }

}