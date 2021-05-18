package com.example.moviesmatch.models.factory;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.moviesmatch.models.Movie;
import com.example.moviesmatch.requests.ImageRequest;
import com.example.moviesmatch.validation.JSONManipulator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MovieFactory {
    ArrayList<Movie> movies;
    JSONManipulator jsonManipulator;
    Movie movie;
    ImageView imagePoster;

    public MovieFactory(){
        jsonManipulator = new JSONManipulator();
    }

    public ArrayList<Movie> createArrayMovies(JSONArray jsonArray){
        movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currentMovie = jsonManipulator.getJSONObjectFromJSONArray(jsonArray, i);
            movies.add(createMovie(currentMovie));
        }
        return movies;
    }

    private Movie createMovie(JSONObject currentMovie){
        movie = new Movie(jsonManipulator.getString(currentMovie, "movId"),
                jsonManipulator.getString(currentMovie, "movTitle"),
                jsonManipulator.getString(currentMovie, "movOverview"),
                jsonManipulator.getString(currentMovie, "movPosterUrl"),
                setMoviePoster(jsonManipulator.getString(currentMovie, "movPosterUrl")),
                Integer.parseInt(jsonManipulator.getString(currentMovie, "movReleaseYear")),
                Integer.parseInt(jsonManipulator.getString(currentMovie, "movImdbRating")),
                Integer.parseInt(jsonManipulator.getString(currentMovie, "movRuntime")),
                jsonManipulator.getString(currentMovie, "movUrl"));
        System.out.println(currentMovie);
        return movie;
    }

    private Bitmap setMoviePoster(String url){
        Bitmap bitmap = null;
        try {
            bitmap = new ImageRequest().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
