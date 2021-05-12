package com.example.moviesmatch.validation;

import com.example.moviesmatch.models.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONManipulator {

    public ArrayList<Genre> toGenreList(JSONArray jsonArray) throws JSONException {
        ArrayList<Genre> listGenres = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try{
                listGenres.add(new Genre(jsonArray.getJSONObject(i).getInt("genId"),
                        jsonArray.getJSONObject(i).getString("genName"),
                        false));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return listGenres;
    }

    public JSONObject newJSONObject(String string){
        JSONObject value = new JSONObject();
        try {
            value = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public String getJSONObjectGetString(JSONObject jsonObject, String objectName, String columnName){
        String value = "";
        try{
            value = jsonObject.getJSONObject(objectName).getString(columnName);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return value;
    }

    public String getString(JSONObject jsonObject, String columnName){
        String value = "";
        try{
            value = jsonObject.getString(columnName);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return value;
    }

    public JSONObject put(JSONObject jsonObject, String columnName, String value){
        try{
            jsonObject.put(columnName, value);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
