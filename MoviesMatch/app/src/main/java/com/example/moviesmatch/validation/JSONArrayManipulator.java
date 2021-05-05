package com.example.moviesmatch.validation;

import com.example.moviesmatch.Genre;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class JSONArrayManipulator {

    public ArrayList<Genre> toGenreList(JSONArray jsonArray){
        ArrayList<Genre> listGenres = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try{
                listGenres.add(new Genre(jsonArray.getJSONObject(i).getInt("genId"), jsonArray.getJSONObject(i).getString("genName"), false));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return listGenres;
    }
}
