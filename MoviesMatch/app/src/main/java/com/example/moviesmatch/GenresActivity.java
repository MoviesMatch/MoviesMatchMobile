package com.example.moviesmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.moviesmatch.async.GetRequest;
import com.example.moviesmatch.async.PostRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.ActivityGenresBinding;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.validation.JSONArrayManipulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import pl.droidsonroids.gif.GifImageView;


public class GenresActivity extends AppCompatActivity implements IGetActivity, IPostActivity {

    private ActivityGenresBinding binding;
    private ListView listViewGenres;
    private ArrayList<Genre> listGenres;
    private GenresListAdapter arrayAdapter;
    private PostRequest postRequest;
    private GetRequest getRequest;
    private JSONObject account;
    private JSONObject jsonAccountWithGenres;
    private JSONArray selectedGenresJson;
    private CertificateByPass certificateByPass;
    private GifImageView gifLoading;
    private Button buttonSavePreferences;

    private final String getGenresURL = "/api/genre/getAllGenres";
    private final String postGenresURL = "/api/genre/addGenresToUser";
    private static int iSelectedGenres = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenresBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUp();
        getGenres();
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
            gifLoading.setVisibility(View.VISIBLE);
            buttonSavePreferences.setEnabled(false);
            
            try{
                jsonAccountWithGenres.put("idUser", account.get("usrId"));
                jsonAccountWithGenres.put("genreIds", selectedGenresJson);
            } catch (JSONException e){
                e.printStackTrace();
            }

            postRequest.postRequest(jsonAccountWithGenres, postGenresURL, new IRequestCallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    startActivity(new Intent(GenresActivity.this, MainActivity.class));
                }
            });
        }else{
            gifLoading.setVisibility(View.GONE);
            buttonSavePreferences.setEnabled(true);
            new AlertDialog.Builder(this).setTitle("You didn't check 5 genres").setMessage("Please check exactly 5 genres of movies you like").show();
        }
    }

    private void getGenres(){
        getRequest.getRequestArray(getGenresURL, new IRequestCallbackArray() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                listGenres = new JSONArrayManipulator().toGenreList(jsonArray);
                Collections.sort(listGenres);
                setArrayAdapter();
            }
        });
    }

    private void setArrayAdapter(){
        arrayAdapter  = new GenresListAdapter(this, listGenres);
        listViewGenres.setAdapter(arrayAdapter);
    }

    private void setSelectedGenresJson(int i){
        selectedGenresJson.put(listGenres.get(i).getId());
        iSelectedGenres++;
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        new AlertDialog.Builder(this).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        new AlertDialog.Builder(this).setTitle("Error").setMessage("Please try again").show();
    }

    private void setUp(){
        postRequest = new PostRequest(this);
        getRequest = new GetRequest(this);
        certificateByPass = new CertificateByPass();
        certificateByPass.IngoreCertificate();
        try {
            account = new JSONObject(getIntent().getStringExtra("Account"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonAccountWithGenres = new JSONObject();
        selectedGenresJson = new JSONArray();
        listViewGenres = binding.listGenres;
        gifLoading = binding.genresLoadingGif;
        buttonSavePreferences = binding.buttonSavePref;
    }
}