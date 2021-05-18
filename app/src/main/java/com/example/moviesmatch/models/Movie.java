package com.example.moviesmatch.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Movie {
    String id;
    String title;
    String overview;
    String posterURL;
    Bitmap imagePoster;
    int releaseYear;
    int imdbRating;
    int runtime;
    String movieURL;

    public Movie(String id, String title, String overview, String posterURL, Bitmap imagePoster, int releaseYear, int imdbRating, int runtime, String movieURL) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterURL = posterURL;
        this.imagePoster = imagePoster;
        this.releaseYear = releaseYear;
        this.imdbRating = imdbRating;
        this.runtime = runtime;
        this.movieURL = movieURL;
    }

    public void setImagePoster(Bitmap imagePoster) {
        this.imagePoster = imagePoster;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public Bitmap getImagePoster(){
        return imagePoster;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getImdbRating() {
        return imdbRating;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getMovieURL() {
        return movieURL;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", posterURL=" + posterURL +
                ", releaseYear=" + releaseYear +
                ", imdbRating=" + imdbRating +
                ", runtime=" + runtime +
                ", movieURL='" + movieURL + '\'' +
                '}';
    }
}
