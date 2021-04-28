package com.example.moviesmatch;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.Arrays;

public class SwipeFragment extends Fragment {
    private SwipeAdapter arrayAdapter;
    private  SwipeFlingAdapterView flingContainer;
    private ArrayList<String> imageUrl;
    private Button buttonLeft, buttonRight;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_swipe, container, false);
        //TEMP hardcoded images
        imageUrl = new ArrayList<>(Arrays.asList("https://image.tmdb.org/t/p/original/2EiAX4eChSWQHwgIFAZbPgXKCJ6.jpg", "https://image.tmdb.org/t/p/original/hkC4yNDFmW1yQuQhtZydMeRuaAb.jpg",
                "https://image.tmdb.org/t/p/original/wUXT3KEh6vjDzwYKiYWwdJNfZOW.jpg", "https://image.tmdb.org/t/p/original/yEcfFXEWpuXcfsR9nKESVCFneqV.jpg", "https://image.tmdb.org/t/p/original/q4FQOiSRhTLWulHl5Vpg37FMArH.jpg"));
        buttonLeft = getView().findViewById(R.id.buttonLeft);
        buttonRight = getView().findViewById(R.id.buttonRight);
        fling();

        return view;
    }

    public void swipeRight(View view){

    }

    public void swipeLeft(View view){

    }






    private void fling(){
        arrayAdapter = new SwipeAdapter(getContext(), imageUrl);

        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                imageUrl.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }

            @Override
            public void onRightCardExit(Object dataObject) {
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
            }

            @Override
            public void onScroll(float v) {

            }



        });
    }
}