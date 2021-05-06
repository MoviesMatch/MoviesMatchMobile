package com.example.moviesmatch.validation;

import com.example.moviesmatch.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONArrayManipulator {

    public ArrayList<Genre> toGenreList(JSONObject jsonObject) throws JSONException {
        ArrayList<Genre> listGenres = new ArrayList<>();
        for (int i = 0; i < jsonObject.getJSONArray("$values").length(); i++) {
            try{
                listGenres.add(new Genre(jsonObject.getJSONArray("$values").getJSONObject(i).getInt("genId"),
                        jsonObject.getJSONArray("$values").getJSONObject(i).getString("genName"),
                        false));
                System.out.println("GENRE ID: " + jsonObject.getJSONArray("$values").getJSONObject(i).getInt("genId"));
                System.out.println("GENRE NAME: " + jsonObject.getJSONArray("$values").getJSONObject(i).getString("genName"));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return listGenres;
    }
}
