package com.example.moviesmatch.layouts.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmatch.databinding.FragmentInfoGroupBinding;
import com.example.moviesmatch.interfaces.IDeleteActivity;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.adapters.RecyclerViewAdapter;
import com.example.moviesmatch.models.Group;
import com.example.moviesmatch.models.MoviesMatchURLS;
import com.example.moviesmatch.requests.DeleteRequest;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.requests.PostRequest;
import com.example.moviesmatch.validation.JSONManipulator;
import com.example.moviesmatch.validation.Loading;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class InfoGroupFragment extends Fragment implements IGetActivity, IPostActivity, IDeleteActivity, IOnBackPressed {
    FragmentInfoGroupBinding binding;
    TextView joinCode, member1, member2;
    EditText groupName;
    RecyclerView recyclerViewGenres;
    GridLayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    Button buttonLeaveGroup, buttonChangeName;
    Group group;
    DeleteRequest deleteRequest;
    GetRequest getRequest;
    PostRequest postRequest;
    JSONManipulator jsonManipulator;
    String token;
    String leaveGroupURL = MoviesMatchURLS.leaveGroupURL;
    String getGroupInfoURL = MoviesMatchURLS.getGroupInfoURL;
    String changeGroupNameURL = MoviesMatchURLS.changeGroupNameURL;
    GifImageView loadingGif;
    Loading loading;
    JSONObject groupInfo;
    JSONObject account;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentInfoGroupBinding.inflate(getLayoutInflater());
        init();
        return binding.getRoot();
    }

    private void getGroupInfo(){
        loading.loadingVisible(loadingGif);
        getGroupInfoURL += group.getGroupId();
        getRequest.getRequest(getGroupInfoURL, token, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                groupInfo = jsonObject;
                setTextViews();
                getGroupGenres();
                loading.loadingGone(loadingGif);
            }
        });
    }

    private void getGroupGenres(){
        ArrayList<String> genresArray = new ArrayList<>();
        JSONArray genres = jsonManipulator.getJSONArrayFromJSONObject(groupInfo, "listGenres");
        for (int i = 0; i < genres.length(); i++) {
            genresArray.add(jsonManipulator.getStringFromJSONArrayAtPosition(genres, "genName", i));
        }
        recyclerViewAdapter = new RecyclerViewAdapter(genresArray, getContext());
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewGenres.setLayoutManager(layoutManager);
        recyclerViewGenres.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void leaveGroup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Leave group");
        builder.setMessage("Are you sure you want to leave " + group.getGroupName() + "?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                String groupId = group.getGroupId();
                String userId = jsonManipulator.getJSONObjectGetString(account, "userDB", "usrId");
                leaveGroupURL += "?groupId=" + groupId + "&userId=" + userId;
                deleteRequest.deleteRequest(leaveGroupURL, token, new IRequestCallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        loading.loadingGone(loadingGif);
                        onBackPressed();
                    }
                });
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void buttonLeaveGroupOnClick(){
        loading.loadingVisible(loadingGif);
        buttonLeaveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveGroup();
            }
        });
    }

    private void buttonChangeNameOnClick(){
        loading.loadingVisible(loadingGif);
        buttonChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupId = group.getGroupId();
                String newGroupName = groupName.getText().toString();
                JSONObject jsonObject = new JSONObject();
                jsonManipulator.put(jsonObject, "groupId", groupId);
                jsonManipulator.put(jsonObject, "newGroupName", newGroupName);
                postRequest.postRequest(jsonObject, changeGroupNameURL, token, new IRequestCallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        loading.loadingGone(loadingGif);
                        new AlertDialog.Builder(getContext()).setTitle("Success").setMessage("The group name has been changed to " + newGroupName).show();
                    }
                });
            }
        });
    }

    private void setTextViews(){
        JSONArray users = jsonManipulator.getJSONArrayFromJSONObject(groupInfo, "listUsers");
        JSONObject user1 = jsonManipulator.getJSONObjectFromJSONArray(users, 0);
        JSONObject user2 = jsonManipulator.getJSONObjectFromJSONArray(users, 1);
        groupName.setText(group.getGroupName());
        joinCode.setText("Join code: " + group.getGroupJoinCode());
        member1.setText(jsonManipulator.getString(user1, "usrFirstname") + " " + jsonManipulator.getString(user1, "usrLastname"));
        member2.setText(jsonManipulator.getString(user2, "usrFirstname") + " " + jsonManipulator.getString(user2, "usrLastname"));
    }

    private void init() {
        groupName = binding.groupName;
        joinCode = binding.joinCode;
        buttonLeaveGroup = binding.buttonLeaveGroup;
        buttonChangeName = binding.buttonChangeName;
        member1 = binding.member1;
        member2 = binding.member2;
        recyclerViewGenres = binding.recyclerViewGenres;
        loadingGif = binding.infoGroupLoadingGif;
        group = getArguments().getParcelable("Group");
        deleteRequest = new DeleteRequest((MainActivity) getActivity());
        getRequest = new GetRequest((MainActivity) getActivity());
        postRequest = new PostRequest((MainActivity)getActivity());
        jsonManipulator = new JSONManipulator();
        loading = new Loading();
        account = jsonManipulator.newJSONObject(getArguments().getString("Account"));
        token = jsonManipulator.getString(account, "token");
        getGroupInfo();
        buttonLeaveGroupOnClick();
        buttonChangeNameOnClick();
    }

    @Override
    public void onBackPressed() {
        ((MainActivity)getActivity()).replaceFrag(new GroupsFragment());
    }

    @Override
    public void onDeleteErrorResponse(int errorCode) {
        loading.loadingGone(loadingGif);
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        loading.loadingGone(loadingGif);
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        loading.loadingGone(loadingGif);
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }
}
