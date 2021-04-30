package com.example.moviesmatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviesmatch.interfaces.OnBackPressed;

public class MatchFragment extends Fragment implements OnBackPressed {
    ImageView imageMatch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        imageMatch = getActivity().findViewById(R.id.imageMatch);
        imageMatch.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onBackPressed() {
        //TODO dans une autre PR
        //OnBackPressed retrourne dans le meme swipefragment
        //((MainActivity)getActivity()).replaceFrag(new SwipeFragment());
    }
}