package com.example.moviesmatch.layouts.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesmatch.R;
import com.example.moviesmatch.databinding.FragmentMovieInfosBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.adapters.RecyclerViewAdapter;
import com.example.moviesmatch.validation.Calculator;
import com.example.moviesmatch.validation.JSONManipulator;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieInfosFragment extends Fragment implements IOnBackPressed {

    private FragmentMovieInfosBinding binding;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;

    private TextView textViewInfoTitle, textViewInfoDate, textViewInfoOverview, textViewRating, textViewRunTime, textViewUrl;
    private ImageView poster;

    private JSONObject movieJson;
    private JSONArray movieGenreJson;

    private JSONManipulator jsonManipulator;
    private Calculator calculator;

    private ArrayList<String> genres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieInfosBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        getJsonObject();
        addInfos();
    }


    public void setUp() {
        genres = new ArrayList<>();
        jsonManipulator = new JSONManipulator();
        calculator = new Calculator();

        recyclerView = binding.recyclerView;
        layoutManager = new GridLayoutManager(getContext(), 2);

        textViewInfoTitle = binding.textViewInfoTitle;
        textViewInfoDate = binding.textViewInfoDate;
        textViewInfoOverview = binding.textViewInfoOverview;
        textViewRating = binding.textViewRating;
        textViewRunTime = binding.textViewRunTime;
        textViewUrl = binding.textViewUrl;
        poster = binding.imageViewPoster;
    }


    public void getJsonObject() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String movieString = (String) bundle.get("movie");
            movieJson = jsonManipulator.newJSONObject(movieString);
            movieGenreJson = jsonManipulator.getJSONArrayFromJSONObject(movieJson, "movieGenreMgrs");
        }
    }

    public void addInfos() {

        textViewInfoTitle.setText(jsonManipulator.getString(movieJson, "movTitle"));
        textViewInfoDate.setText(jsonManipulator.getString(movieJson, "movReleaseYear"));
        textViewInfoOverview.setText(jsonManipulator.getString(movieJson, "movOverview"));
        textViewRating.setText( calculator.rating(jsonManipulator.getString(movieJson, "movImdbRating")));
        textViewRunTime.setText(calculator.runTime(jsonManipulator.getString(movieJson, "movRuntime")));
        textViewUrl.setText(jsonManipulator.getString(movieJson, "movUrl"));
        Picasso.get().load(jsonManipulator.getString(movieJson, "movPosterUrl")).into(poster);

        for (int i = 0; i < movieGenreJson.length(); i++) {
            genres.add(jsonManipulator.getStringFromJSONArrayAtPosition(movieGenreJson,"genName", i));
        }

        initRecyclerView();
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
        } else {
        }
    }
}