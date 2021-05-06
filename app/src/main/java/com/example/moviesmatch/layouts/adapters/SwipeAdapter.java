package com.example.moviesmatch.layouts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviesmatch.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SwipeAdapter extends ArrayAdapter<JSONObject> {

    private Context mContext;
    private List<JSONObject> moviesList;

    public SwipeAdapter(@NonNull Context context, ArrayList<JSONObject> list) {
        super(context, 0, list);
        mContext = context;
        moviesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;

        if (convertView == null){
            row = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            } else{
            row = convertView;
        }

        JSONObject currentMovie = moviesList.get(position);

        ImageView image = row.findViewById(R.id.imageSwipe);
        TextView title = row.findViewById(R.id.textViewTitle);
        TextView date = row.findViewById(R.id.textViewDate);

        try {
            Picasso.get().load(currentMovie.getString("movPosterUrl")).into(image);
            title.setText(currentMovie.getString("movTitle"));
            date.setText(currentMovie.getString("movReleaseYear"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return row;
    }
}