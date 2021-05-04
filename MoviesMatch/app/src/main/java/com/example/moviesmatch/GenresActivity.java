package com.example.moviesmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.moviesmatch.async.PostRequest;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class GenresActivity extends AppCompatActivity implements IGetActivity, IPostActivity {

    private ListView listViewGenres;
    private ArrayList<Genre> listGenres;
    private GenresListAdapter arrayAdapter;
    private PostRequest postRequest;
    private JSONObject selectedGenresJson;

    private final String getGenresURL = "api/genre/getGenres";
    private final String postGenresURL = "api/genre/postGenres";
    private static int iSelectedGenres = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);
        postRequest = new PostRequest(this);
        selectedGenresJson = new JSONObject();
        
        listViewGenres = findViewById(R.id.listGenres);
        listGenres = new ArrayList<Genre>(Arrays.asList(new Genre("Action", false), new Genre("Adventure", false), new Genre("Horror", false), new Genre("Comedy", false), new Genre("Thriller", false), new Genre("Science-Fiction", false), new Genre("Romance", false)));
        arrayAdapter  = new GenresListAdapter(this, listGenres);
        listViewGenres.setAdapter(arrayAdapter);

    }

    private boolean isFiveChecked(){
        int numberChecked = 0 ;
        for(int i = 0; i < listGenres.size();i++){
            if(listGenres.get(i).isChecked()){
                numberChecked++;
                setSelectedGenresJson(i);
            }

            if(numberChecked == 5){
              return true;
            }
        }
        return  false;
    }

    public void savePrefs(View view){
        if(isFiveChecked()){
            System.out.println(selectedGenresJson);
            postRequest.postRequest(selectedGenresJson, postGenresURL, new IRequestCallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    startActivity(new Intent(GenresActivity.this, MainActivity.class));
                }
            });
        }else{
            new AlertDialog.Builder(this).setTitle("You didn't check 5 genres").setMessage("Please check exactly 5 genres of movies you like").show();
        }

    }

    private void setSelectedGenresJson(int i){
        try {
            selectedGenresJson.put("Genre" + iSelectedGenres, listGenres.get(i).ItemString);
            iSelectedGenres++;
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        new AlertDialog.Builder(this).setTitle("Error").setMessage("Please try again").show();
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        new AlertDialog.Builder(this).setTitle("Error").setMessage("Please try again").show();
    }
}