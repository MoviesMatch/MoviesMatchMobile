package com.example.moviesmatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupsFragment extends Fragment {

    private ListView listViewGroups;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        //TEST
        listViewGroups = view.findViewById(R.id.listViewGroups);
        ArrayList arrayListGroups = new ArrayList<>(Arrays.asList("GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3"));
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, arrayListGroups);
        listViewGroups.setAdapter(arrayAdapter);
        return view;
    }
}