package com.example.moviesmatch.layouts.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesmatch.databinding.FragmentMovieInfosBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.layouts.adapters.RecyclerViewAdapter;
import com.example.moviesmatch.validation.Calculator;
import com.example.moviesmatch.validation.JSONManipulator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieInfosFragment extends Fragment implements IOnBackPressed {

    private FragmentMovieInfosBinding binding;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TextView textViewInfoTitle, textViewInfoDate, textViewInfoOverview, textViewRating, textViewRunTime;
    private ImageView poster;
    private Calculator calculator;
    private ArrayList<String> genres;
    private String title, overview, posterUrl, releaseYear, imdbRating, runtime, url;
    private Button buttonUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieInfosBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getJsonObject();
        addInfos();
    }

    public void getJsonObject() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            title = bundle.getString("Title");
            overview = bundle.getString("Overview");
            posterUrl = bundle.getString("PosterURL");
            releaseYear = bundle.getString("ReleaseYear");
            imdbRating = bundle.getString("ImdbRating");
            runtime = bundle.getString("Runtime");
            url = bundle.getString("URL");
            genres = bundle.getStringArrayList("Genres");
        }
    }

    public void addInfos() {
        textViewInfoTitle.setText(title);
        textViewInfoDate.setText(releaseYear);
        textViewInfoOverview.setText(overview);
        textViewRating.setText(calculator.rating(imdbRating));
        textViewRunTime.setText(calculator.runTime(runtime));
        buttonUrlOnClick(url);
        Picasso.get().load(posterUrl).into(poster);
        initRecyclerView();
    }

    private void buttonUrlOnClick(String netflixUrl){
        buttonUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(netflixUrl);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }

    public void init() {
        genres = new ArrayList<>();
        calculator = new Calculator();

        recyclerView = binding.recyclerView;
        layoutManager = new GridLayoutManager(getContext(), 2);

        textViewInfoTitle = binding.textViewInfoTitle;
        textViewInfoDate = binding.textViewInfoDate;
        textViewInfoOverview = binding.textViewOverview;
        textViewRating = binding.textViewRating;
        textViewRunTime = binding.textViewRunTime;
        buttonUrl = binding.buttonUrl;
        poster = binding.imageViewPoster;
    }

    public void initRecyclerView() {
        recyclerViewAdapter = new RecyclerViewAdapter(genres, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }
}