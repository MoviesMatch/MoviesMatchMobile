package com.example.moviesmatch.models.factory;

import android.graphics.drawable.Drawable;

import com.example.moviesmatch.models.Movie;
import com.example.moviesmatch.requests.ImageRequest;
import com.example.moviesmatch.validation.JSONManipulator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MovieFactory {
    ArrayList<Movie> movies;
    JSONManipulator jsonManipulator;
    Movie movie;

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
                jsonManipulator.getString(currentMovie, "movReleaseYear"),
                jsonManipulator.getString(currentMovie, "movImdbRating"),
                jsonManipulator.getString(currentMovie, "movRuntime"),
                jsonManipulator.getString(currentMovie, "movUrl"));
        System.out.println("Drawable " + currentMovie);
        return movie;
    }

    private Drawable setMoviePoster(String url){
        Drawable drawable = null;
        try {
            drawable = new ImageRequest().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return drawable;
    }
}
