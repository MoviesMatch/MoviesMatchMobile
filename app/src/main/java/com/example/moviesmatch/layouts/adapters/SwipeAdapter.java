package com.example.moviesmatch.layouts.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviesmatch.R;
import com.example.moviesmatch.models.Movie;
import com.example.moviesmatch.requests.ImageRequest;
import com.example.moviesmatch.validation.JSONManipulator;
import java.util.ArrayList;
import java.util.List;

public class SwipeAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private List<Movie> moviesList;

    public SwipeAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0, list);
        mContext = context;
        moviesList = list;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        if (convertView == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        } else {
            row = convertView;
        }

        Movie currentMovie = moviesList.get(position);

        ImageView image = row.findViewById(R.id.imageSwipe);
        TextView title = row.findViewById(R.id.textViewTitle);
        TextView date = row.findViewById(R.id.textViewDate);

        image.setImageDrawable(currentMovie.getImagePoster());
        title.setText(currentMovie.getTitle());
        date.setText(String.valueOf(currentMovie.getReleaseYear()));
        System.out.println("RETURN ROW");
        return row;
    }
}