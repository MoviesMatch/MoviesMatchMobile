package com.example.moviesmatch.layouts.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SwipeAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private List<Movie> moviesList;
    private JSONManipulator jsonManipulator;
    private ImageRequest imageRequest;
    Bitmap bitmap;

    public SwipeAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0, list);
        mContext = context;
        moviesList = list;
        jsonManipulator = new JSONManipulator();
        imageRequest = new ImageRequest();
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

        //Picasso.get().load(currentMovie.getPosterURL()).into(image);
        image.setImageBitmap(currentMovie.getImagePoster());
        title.setText(currentMovie.getTitle());
        date.setText(String.valueOf(currentMovie.getReleaseYear()));
        System.out.println("RETURN ROW");
        return row;
    }
}