package com.example.moviesmatch;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class SwipeFragment extends Fragment {
    private ArrayList<ImageView> al;
    private SwipeAdapter arrayAdapter;
    private ArrayList<String> imageUrl;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_swipe, container, false);
        al = new ArrayList<>();
        //TEMP hardcoded images
        imageUrl = new ArrayList<>(Arrays.asList("https://image.tmdb.org/t/p/original/2EiAX4eChSWQHwgIFAZbPgXKCJ6.jpg", "https://image.tmdb.org/t/p/original/hkC4yNDFmW1yQuQhtZydMeRuaAb.jpg",
                "https://image.tmdb.org/t/p/original/wUXT3KEh6vjDzwYKiYWwdJNfZOW.jpg", "https://image.tmdb.org/t/p/original/yEcfFXEWpuXcfsR9nKESVCFneqV.jpg", "https://image.tmdb.org/t/p/original/q4FQOiSRhTLWulHl5Vpg37FMArH.jpg"));

        setImageView();
        fling();
        return view;
    }

    private void setImageView(){
        for (String url : imageUrl) {
            ImageView imageView = new ImageView(getContext());
            Picasso.get().load(url).into(imageView);
            al.add(imageView);
        }
    }

    private void fling(){
        arrayAdapter = new SwipeAdapter(getContext(), al);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
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
                imageUrl = new ArrayList<>(Arrays.asList("https://image.tmdb.org/t/p/original/2EiAX4eChSWQHwgIFAZbPgXKCJ6.jpg", "https://image.tmdb.org/t/p/original/hkC4yNDFmW1yQuQhtZydMeRuaAb.jpg",
                        "https://image.tmdb.org/t/p/original/wUXT3KEh6vjDzwYKiYWwdJNfZOW.jpg", "https://image.tmdb.org/t/p/original/yEcfFXEWpuXcfsR9nKESVCFneqV.jpg", "https://image.tmdb.org/t/p/original/q4FQOiSRhTLWulHl5Vpg37FMArH.jpg"));
                setImageView();
            }

            @Override
            public void onScroll(float v) {

            }
        });
    }
}