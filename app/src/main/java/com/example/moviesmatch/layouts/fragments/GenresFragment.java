package com.example.moviesmatch.layouts.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.layouts.adapters.GenresListAdapter;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.requests.PostRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentGenresBinding;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.layouts.activities.CreateAccountActivity;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.models.Genre;
import com.example.moviesmatch.validation.JSONArrayManipulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import pl.droidsonroids.gif.GifImageView;


public class GenresFragment extends Fragment implements IGetActivity, IPostActivity {

    private FragmentGenresBinding binding;
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
    private String parent;
    private ImageView imageLogo;
    private LayoutInflater inflater;

    private final String getGenresURL = "/api/genre/getAllGenres";
    private final String postGenresURL = "/api/genre/addGenresToUser";
    private String getUserGenreURL = "/api/genre/getUserGenres";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentGenresBinding.inflate(getLayoutInflater());
        this.inflater = inflater;
        setUp();
        getGenres();
        savePrefs();
        return binding.getRoot();
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

    public void savePrefs(){
        buttonSavePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFiveChecked()){
                    loading();
                    try{
                        jsonAccountWithGenres.put("idUser", account.get("usrId"));
                        jsonAccountWithGenres.put("genreIds", selectedGenresJson);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                    postRequest.postRequest(jsonAccountWithGenres, postGenresURL, new IRequestCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.putExtra("Account", account.toString());
                            startActivity(intent);
                        }
                    });
                }else{
                    loadingGone();
                    new AlertDialog.Builder(getContext()).setTitle("You didn't check 5 genres").setMessage("Please check exactly 5 genres of movies you like").show();
                }
            }
        });
    }

    /**
     * Get all available genres from the server
     */
    private void getGenres(){
        loading();
        getRequest.getRequest(getGenresURL, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try{
                    listGenres = new JSONArrayManipulator().toGenreList(jsonObject);
                    if (parent.equals("MainActivity")){
                        getUserGenres();
                    } else {
                        setArrayAdapter(listGenres);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
                loadingGone();
                System.out.println("getGenres fini");
            }
        });
    }

    /**
     * Gets the loggedIn users selected Genres to be checked in the listView
     */
    private void getUserGenres(){
        setUserGenreURL();
        getRequest.getRequest(getUserGenreURL, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try{
                    ArrayList<Genre> userGenre = new JSONArrayManipulator().toGenreList(jsonObject);
                    for (Genre genres : listGenres){
                        for (Genre userGenres : userGenre){
                            if (genres.getId() == userGenres.getId()){
                                genres.setChecked(true);
                            }
                        }
                    }
                    setArrayAdapter(listGenres);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                System.out.println("getUserGenres fini");
            }
        });
    }

    /**
     * Sets the URL with the loggedIn user id in parameter
     */
    private void setUserGenreURL(){
        String usrId = "";
        try{
            usrId = account.getString("usrId");
        } catch (JSONException e){
            e.printStackTrace();
        }
        getUserGenreURL += "?idUser=" + usrId;
    }

    private void setArrayAdapter(ArrayList<Genre> listGenres){
        Collections.sort(listGenres);
        arrayAdapter  = new GenresListAdapter(getContext(), listGenres);
        listViewGenres.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    private void setSelectedGenresJson(int i){
        selectedGenresJson.put(listGenres.get(i).getId());
        System.out.println("Selected : " + listGenres.get(i).getItemString());
    }

    private void loading(){
        gifLoading.setVisibility(View.VISIBLE);
        buttonSavePreferences.setEnabled(false);
    }

    private void loadingGone(){
        gifLoading.setVisibility(View.GONE);
        buttonSavePreferences.setEnabled(true);
    }

    private void imageGone(){
        if (parent.equals("MainActivity")){
            imageLogo.setVisibility(View.GONE);
        }
    }

    private void setParent(){
        //Gets the parent since this fragment is used in 2 activities
        if (this.getArguments().getString("Parent").equals("CreateAccountActivity")){
            parent = "CreateAccountActivity";
            postRequest = new PostRequest((CreateAccountActivity)getActivity());
            getRequest = new GetRequest((CreateAccountActivity)getActivity());
        } else {
            parent = "MainActivity";
            postRequest = new PostRequest((MainActivity)getActivity());
            getRequest = new GetRequest((MainActivity)getActivity());
        }
    }

    private void setUp(){
        setParent();
        certificateByPass = new CertificateByPass();
        certificateByPass.IngoreCertificate();
        try {
            account = new JSONObject(this.getArguments().getString("Account"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonAccountWithGenres = new JSONObject();
        selectedGenresJson = new JSONArray();
        listViewGenres = binding.listGenres;
        gifLoading = binding.genresLoadingGif;
        buttonSavePreferences = binding.buttonSavePref;
        imageLogo = binding.imageViewLogo;
        imageGone();
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        loadingGone();
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        loadingGone();
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }
}