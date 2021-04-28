package com.example.moviesmatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SwipeAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> moviesList;

    public SwipeAdapter(@NonNull Context context, ArrayList<String> list) {
        super(context, 0, list);
        mContext = context;
        moviesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String currentMovie = moviesList.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);

        ImageView image = convertView.findViewById(R.id.imageSwipe);
        Picasso.get().load(currentMovie).into(image);
        System.out.println(currentMovie);
        return convertView;
    }
}