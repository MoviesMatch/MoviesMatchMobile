package com.example.moviesmatch.models.factory;

import com.example.moviesmatch.models.Group;
import com.example.moviesmatch.validation.JSONManipulator;

import org.json.JSONObject;

public class GroupFactory {
    JSONManipulator jsonManipulator;

    public GroupFactory(){
        jsonManipulator = new JSONManipulator();
    }

    public Group createGroup(JSONObject jsonObject){
        return new Group(jsonManipulator.getString(jsonObject, "grpId"),
                jsonManipulator.getString(jsonObject, "grpJoinCode"),
                jsonManipulator.getString(jsonObject, "grpName"));
    }
}
