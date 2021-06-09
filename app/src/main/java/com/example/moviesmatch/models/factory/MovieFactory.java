package com.example.moviesmatch.models.factory;

import com.example.moviesmatch.models.Movie;
import com.example.moviesmatch.validation.JSONManipulator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
                jsonManipulator.getString(currentMovie, "movReleaseYear"),
                jsonManipulator.getString(currentMovie, "movImdbRating"),
                jsonManipulator.getString(currentMovie, "movRuntime"),
                jsonManipulator.getString(currentMovie, "movUrl"),
                setMovieGenres(currentMovie));
        return movie;
    }

    private ArrayList<String> setMovieGenres(JSONObject currentMovie){
        ArrayList<String> genresArrayList = new ArrayList<>();
        JSONArray genres = jsonManipulator.getJSONArrayFromJSONObject(currentMovie, "movieGenreMgrs");
        for (int i = 0; i < genres.length(); i++) {
            JSONObject genre = jsonManipulator.getJSONObjectFromJSONArray(genres, i);
            genresArrayList.add(jsonManipulator.getString(genre, "genName"));
        }
        return genresArrayList;
    }
}
