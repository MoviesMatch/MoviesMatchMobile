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

import com.example.moviesmatch.R;
import com.example.moviesmatch.layouts.adapters.RecyclerViewAdapter;

import java.util.ArrayList;

public class MovieInfosFragment extends Fragment {

    private ConstraintLayout constraintLayout;
    private View view;

    private ArrayList<String> genres = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_infos, container, false);
        constraintLayout = view.findViewById(R.id.constraintLayoutInfosMovie);
        init();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void init(){
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Horror");
        genres.add("Thriller");
        genres.add("Comedy");
        genres.add("Tragedy");

        initRecyclerView();
    }

    public void initRecyclerView(){
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);

        RecyclerView recyclerView =  view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(genres, getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

}