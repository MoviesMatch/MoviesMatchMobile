package com.example.moviesmatch.layouts.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesmatch.R;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentMatchBinding;
import com.example.moviesmatch.databinding.FragmentSwipeBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.adapters.RecyclerViewAdapter;
import com.example.moviesmatch.layouts.adapters.RecyclerViewMatchAdapter;
import com.example.moviesmatch.models.Movie;
import com.example.moviesmatch.models.MoviesMatchURLS;
import com.example.moviesmatch.models.factory.MovieFactory;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.validation.JSONManipulator;
import com.example.moviesmatch.validation.Loading;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class MatchFragment extends Fragment implements IOnBackPressed {

    FragmentMatchBinding binding;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RecyclerViewMatchAdapter recyclerViewMatchAdapter;
    private TextView textViewMessage;

    private ArrayList<Movie> movies;

    private String groupId;

    private String getUrlMatchs;

    private String token;

    private MoviesMatchURLS moviesMatchURLS;

    private GetRequest getRequest;

    private CertificateByPass certificat;

    private Loading loading;
    private GifImageView loadingGif;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMatchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        getMatchMovies();
    }


    private void setUp() {
        recyclerView = binding.recyclerViewMovies;
        textViewMessage = binding.textViewMessage;
        loadingGif = binding.matchLoadingGif;
        layoutManager = new GridLayoutManager(getContext(), 3);

        movies = new ArrayList<>();

        moviesMatchURLS = new MoviesMatchURLS();

        getRequest = new GetRequest((AppCompatActivity) getContext());
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();

        loading = new Loading();
    }


    private void getIdGroupUrl() {
        token = getArguments().getString("Token");
        groupId = getArguments().getString("GroupId");

        getUrlMatchs = moviesMatchURLS.getMatchURL;
        getUrlMatchs += "?idGroup=" + groupId;
    }


    private void getMatchMovies() {
        loading.loadingVisible(loadingGif);
        getIdGroupUrl();
        getRequest.getRequestArray(getUrlMatchs, token, new IRequestCallbackArray() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                loading.loadingGone(loadingGif);
                movies = new MovieFactory().createArrayMovies(jsonArray);
                if (movies.isEmpty()) {
                    textViewMessage.setVisibility(View.VISIBLE);
                } else {
                    textViewMessage.setVisibility(View.GONE);
                }
                initRecyclerView();
            }
        });
    }


    public void initRecyclerView() {
        recyclerViewMatchAdapter = new RecyclerViewMatchAdapter(movies, getContext(), ((MainActivity) getActivity()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewMatchAdapter);
        recyclerViewMatchAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        ((MainActivity) getActivity()).replaceFrag(new GroupsFragment());
    }
}