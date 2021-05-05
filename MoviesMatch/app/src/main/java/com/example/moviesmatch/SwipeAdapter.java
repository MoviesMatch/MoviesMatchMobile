package com.example.moviesmatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        JSONObject currentMovie = moviesList.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);

        ImageView image = convertView.findViewById(R.id.imageSwipe);
        TextView title = convertView.findViewById(R.id.textViewTitre);
        TextView date = convertView.findViewById(R.id.textViewDate);

        try {
            Picasso.get().load(currentMovie.getString("movPosterUrl")).into(image);
            title.setText(currentMovie.getString("movTitle"));
            date.setText(currentMovie.getString("movReleaseYear"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}