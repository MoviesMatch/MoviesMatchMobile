package com.example.moviesmatch.layouts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviesmatch.R;
import com.example.moviesmatch.interfaces.IOnClickGroupInfoListener;
import com.example.moviesmatch.models.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupsAdapter extends ArrayAdapter<Group> {

    private Context mContext;
    private List<Group> groupsList;
    private IOnClickGroupInfoListener IOnClickGroupInfoListener;

    public GroupsAdapter(@NonNull Context context, ArrayList<Group> list, IOnClickGroupInfoListener IOnClickGroupInfoListener) {
        super(context, 0, list);
        mContext = context;
        groupsList = list;
        this.IOnClickGroupInfoListener = IOnClickGroupInfoListener;
        Collections.sort(groupsList);
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
        ImageView imageInfoGroup = row.findViewById(R.id.imageGroupInfo);
        infoGroupOnClick(imageInfoGroup, currentGroup);

        nameGroup.setText(currentGroup.getGroupName());
        joinCode.setText("Join code: "  + currentGroup.getGroupJoinCode());

        return row;
    }

    public void infoGroupOnClick(ImageView imageView, Group group){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IOnClickGroupInfoListener.onGroupInfoClicked(group);
            }
        });
    }
}
