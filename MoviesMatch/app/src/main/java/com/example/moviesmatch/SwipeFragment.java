package com.example.moviesmatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moviesmatch.async.GetRequest;
import com.example.moviesmatch.databinding.FragmentSwipeBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SwipeFragment extends Fragment implements IOnBackPressed {
    private SwipeAdapter arrayAdapter;
    private SwipeFlingAdapterView flingContainer;
    private ArrayList<String> imageUrl;
    private Button buttonLeft, buttonRight;


    private GetRequest getReq;
    private ArrayList listArray;
    final static String URL = "/api/";


    private FragmentSwipeBinding binding ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSwipeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TEMP hardcoded images
        imageUrl = new ArrayList<>(Arrays.asList("https://image.tmdb.org/t/p/original/2EiAX4eChSWQHwgIFAZbPgXKCJ6.jpg", "https://image.tmdb.org/t/p/original/hkC4yNDFmW1yQuQhtZydMeRuaAb.jpg",
                "https://image.tmdb.org/t/p/original/wUXT3KEh6vjDzwYKiYWwdJNfZOW.jpg", "https://image.tmdb.org/t/p/original/yEcfFXEWpuXcfsR9nKESVCFneqV.jpg", "https://image.tmdb.org/t/p/original/q4FQOiSRhTLWulHl5Vpg37FMArH.jpg"));
        buttonLeft = binding.buttonLeft;
        buttonRight = binding.buttonRight;
        setUp();

    }

    public void swipeButtons() {
       buttonRight.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               flingContainer.getTopCardListener().selectLeft();
           }
       });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });


    }

    private void fillListFilm(){
        getReq.getRequestArray(URL, new IRequestCallbackArray() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
               
            }
        });
    }


    public void setUp(){
        getReq = new GetRequest((MainActivity)getActivity());
        fling();
        swipeButtons();

    }

    private void fling() {
        arrayAdapter = new SwipeAdapter(getContext(), imageUrl);

        flingContainer =binding.frame;
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

    @Override
    public void onBackPressed() {
       // imageMatch.setVisibility(View.GONE);
        ((MainActivity)getActivity()).replaceFrag(new GroupsFragment());
    }
}