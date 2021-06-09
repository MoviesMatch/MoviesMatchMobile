package com.example.moviesmatch.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movie implements Parcelable {
    String id;
    String title;
    String overview;
    String posterURL;
    Bitmap imagePoster;
    String releaseYear;
    String imdbRating;
    String runtime;
    String movieURL;
    ArrayList<String> genres;

    public Movie(String id, String title, String overview, String posterURL,String releaseYear, String imdbRating, String runtime, String movieURL, ArrayList<String> genres) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterURL = posterURL;
        this.releaseYear = releaseYear;
        this.imdbRating = imdbRating;
        this.runtime = runtime;
        this.movieURL = movieURL;
        this.genres = genres;
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

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getMovieURL() {
        return movieURL;
    }

    public ArrayList<String> getGenres() {
        return genres;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
