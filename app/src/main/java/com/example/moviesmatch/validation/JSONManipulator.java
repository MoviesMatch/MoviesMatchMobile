package com.example.moviesmatch.validation;

import com.example.moviesmatch.models.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONManipulator {

    public ArrayList<Genre> toGenreList(JSONArray jsonArray) {
        ArrayList<Genre> listGenres = new ArrayList<>();
        try{
            for (int i = 0; i < jsonArray.length(); i++) {
                listGenres.add(new Genre(jsonArray.getJSONObject(i).getInt("genId"),
                        jsonArray.getJSONObject(i).getString("genName"),
                        false));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return listGenres;
    }

    public JSONObject newJSONObject(String string) {
        JSONObject value = new JSONObject();
        try {
            value = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public String getJSONObjectGetString(JSONObject jsonObject, String objectName, String columnName) {
        String value = "";
        try {
            value = jsonObject.getJSONObject(objectName).getString(columnName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public String getString(JSONObject jsonObject, String columnName) {
        String value = "";
        try {
            value = jsonObject.getString(columnName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public Boolean getBoolean(JSONObject jsonObject, String columnName) {
        Boolean value = false;
        try {
            value = jsonObject.getBoolean(columnName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }



    public JSONObject put(JSONObject jsonObject, String columnName, Object value) {
        try {
            jsonObject.put(columnName, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray getJSONArrayFromJSONObject(JSONObject jsonObject, String columnName){
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray(columnName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONObject getJSONObjectFromJSONArray(JSONArray jsonArray, int position){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = jsonArray.getJSONObject(position);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getStringFromJSONArrayAtPosition(JSONArray jsonArray, String columnName, int position){
        String string = "";
        try {
            string = jsonArray.getJSONObject(position).getString(columnName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return string;
    }
}
