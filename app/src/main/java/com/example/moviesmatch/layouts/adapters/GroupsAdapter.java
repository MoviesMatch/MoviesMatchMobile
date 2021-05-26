package com.example.moviesmatch.layouts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviesmatch.R;
import com.example.moviesmatch.models.Group;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupsAdapter extends ArrayAdapter<Group> {

    private Context mContext;
    private List<Group> groupsList;

    public GroupsAdapter(@NonNull Context context, ArrayList<Group> list) {
        super(context, 0, list);
        mContext = context;
        groupsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;

        if (convertView == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.row_group, parent, false);
        } else {
            row = convertView;
        }

        Group currentGroup = groupsList.get(position);
        TextView nameGroup = row.findViewById(R.id.textViewNameGroup);
        TextView joinCode = row.findViewById(R.id.textViewJoinCode);

        nameGroup.setText(currentGroup.getGroupName());
        joinCode.setText("Join code: "  + currentGroup.getGroupJoinCode());

        return row;
    }
}
