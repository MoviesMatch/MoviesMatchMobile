package com.example.moviesmatch;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupsFragment extends Fragment {
    private ListView listViewGroups;
    private ConstraintLayout constraintLayout;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_groups, container, false);
        constraintLayout = view.findViewById(R.id.constraintLayoutGroups);
        onClickGroup();
        return view;
    }

    private void onClickGroup() {
        //TEST
        listViewGroups = view.findViewById(R.id.listViewGroups);
        ArrayList arrayListGroups = new ArrayList<>(Arrays.asList("GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3"));
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, arrayListGroups);
        listViewGroups.setAdapter(arrayAdapter);
        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = arrayAdapter.getItem(position);
                System.out.println(item);
                //Fragment groups_fragment opened when MainActivity is called
                ((MainActivity)getActivity()).replaceFrag(new SwipeFragment());
            }
        });
    }
}