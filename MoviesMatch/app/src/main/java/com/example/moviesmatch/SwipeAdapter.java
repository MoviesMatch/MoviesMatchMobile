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

import java.util.ArrayList;
import java.util.List;

public class SwipeAdapter extends ArrayAdapter<ImageView> {

    private Context mContext;
    private List<ImageView> moviesList = new ArrayList<>();

    public SwipeAdapter(@NonNull Context context, ArrayList<ImageView> list) {
        super(context, 0, list);
        mContext = context;
        moviesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);

        ImageView currentMovie = moviesList.get(position);

        ImageView image = (ImageView) listItem.findViewById(R.id.image);
        image.setImageDrawable(currentMovie.getDrawable());

        return listItem;
    }
}