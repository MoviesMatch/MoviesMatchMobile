package com.example.moviesmatch;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class SwipeFragment extends Fragment {
    private ArrayList<ImageView> al;
    private SwipeAdapter arrayAdapter;
    private int i;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe, container, false);
        //TEMP hardcoded images
        ImageView img1 = new ImageView(getContext());
        ImageView img2 = new ImageView(getContext());
        ImageView img3 = new ImageView(getContext());
        ImageView img4 = new ImageView(getContext());
        ImageView img5 = new ImageView(getContext());

        img1.setImageResource(R.drawable.bob);
        img2.setImageResource(R.drawable.bobdeux);
        img3.setImageResource(R.drawable.bobtrois);
        img4.setImageResource(R.drawable.ppexpress);
        img5.setImageResource(R.drawable.myfairlady);

        al = new ArrayList<>();
        al.add(img1);
        al.add(img2);
        al.add(img3);
        al.add(img4);
        al.add(img5);

        arrayAdapter = new SwipeAdapter(getContext(), al);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(getContext(), "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here

            }

            @Override
            public void onScroll(float v) {

            }


        });


        return view;
    }

}