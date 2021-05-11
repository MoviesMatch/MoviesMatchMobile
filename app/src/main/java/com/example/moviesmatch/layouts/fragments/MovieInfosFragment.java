package com.example.moviesmatch.layouts.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviesmatch.R;
import com.example.moviesmatch.databinding.FragmentMovieInfosBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.adapters.RecyclerViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieInfosFragment extends Fragment  implements IOnBackPressed {

    private FragmentMovieInfosBinding binding;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;

    private TextView textViewInfoTitle, textViewInfoDate, textViewInfoOverview;

    private JSONObject movieJson;

    private ArrayList<String> genres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieInfosBinding.inflate(getLayoutInflater());
        getJsonObject();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        addInfos();
    }


    public void setUp() {
        genres = new ArrayList<>();

        recyclerView = binding.recyclerView;
        layoutManager = new GridLayoutManager(getContext(), 2);

        textViewInfoTitle = binding.textViewInfoTitle;
        textViewInfoDate = binding.textViewInfoDate;
        textViewInfoOverview = binding.textViewInfoOverview;
    }


    public void getJsonObject() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String movieString = (String) bundle.get("movie");
            System.out.println(bundle);
            System.out.println(movieString);

            movieJson = new JSONObject();
            try {
                movieJson = new JSONObject(movieString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void addInfos() {
        try {
            textViewInfoTitle.setText(movieJson.getString("movTitle"));
            textViewInfoDate.setText(movieJson.getString("movReleaseYear"));
            textViewInfoOverview.setText(movieJson.getString("movOverview"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        genres.add("Action");
        genres.add("Adventure");
        genres.add("Horror");
        genres.add("Thriller");
        genres.add("Comedy");
        genres.add("Tragedy");


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
        System.out.println("Retour au swipe");
    }
}