package com.example.moviesmatch.layouts.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesmatch.R;
import com.example.moviesmatch.databinding.FragmentGroupsBinding;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.adapters.GroupsAdapter;
import com.example.moviesmatch.models.Group;
import com.example.moviesmatch.models.MoviesMatchURLS;
import com.example.moviesmatch.models.factory.GroupFactory;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.requests.PostRequest;
import com.example.moviesmatch.validation.InputsValidation;
import com.example.moviesmatch.validation.JSONManipulator;
import com.example.moviesmatch.validation.Loading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class GroupsFragment extends Fragment implements IGetActivity, IPostActivity, IOnBackPressed {
    private ListView listViewGroups;
    private FragmentGroupsBinding binding;
    private JSONObject account;
    private ArrayList arrayListGroups;
    private GroupsAdapter adapter;
    private GetRequest getRequest;
    private PostRequest postRequest;
    private String userGroupsURL ;
    private String createGroupURL = MoviesMatchURLS.createGroupURL;
    private String joinGroupURL = MoviesMatchURLS.joinGroupURL;
    private String token;
    private String userId;
    private JSONManipulator jsonManipulator;
    private InputsValidation validation;
    private Button createGroupButton, joinGroupButton;
    private EditText createGroupEditText, joinGroupEditText;
    private TextView noGroupsTextView;
    private GifImageView loadingGif;
    private Loading loading;
    private GroupFactory groupFactory;
    private int exitCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(getLayoutInflater());
        init();
        displayGroups();
        createGroupButtonOnClick();
        joinGroupButtonOnClick();
        return binding.getRoot();
    }

    private void amountOfGroups(){
        if (listViewGroups.getAdapter().getCount() == 0){
            noGroupsTextView.setVisibility(View.VISIBLE);
        } else {
            noGroupsTextView.setVisibility(View.GONE);
        }
    }

    private void displayGroups() {
        getGroupUser();
        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loading.loadingVisible(loadingGif, createGroupButton, joinGroupButton);
                Group group = adapter.getItem(position);
                getUsersInGroup(group);
            }
        });
    }

    private void getGroupUser() {
        loading.loadingVisible(loadingGif, createGroupButton, joinGroupButton);
        arrayListGroups.clear();
        getRequest.getRequestArray(userGroupsURL, token, new IRequestCallbackArray() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayListGroups.add(groupFactory.createGroup(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new GroupsAdapter(getContext(), arrayListGroups, (MainActivity)getActivity());
                listViewGroups.setAdapter(adapter);
                amountOfGroups();
                loading.loadingGone(loadingGif, createGroupButton, joinGroupButton);
            }
        });
    }

    private void createGroupButtonOnClick() {
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation.validateInputNotEmpty(createGroupEditText.getText().toString(), "Create Group", "You must give a name to your group")) {
                    createGroup();
                }
            }
        });
    }

    private void joinGroupButtonOnClick() {
        joinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation.validateInputNotEmpty(joinGroupEditText.getText().toString(), "Join Group", "You must write a valid code")) {
                    joinGroup();
                }
            }
        });
    }


    private void createGroup() {
        loading.loadingVisible(loadingGif, createGroupButton, joinGroupButton);
        JSONObject createGroupJSON = new JSONObject();
        createGroupJSON = jsonManipulator.put(createGroupJSON, "groupName", createGroupEditText.getText().toString());
        createGroupJSON = jsonManipulator.put(createGroupJSON, "userId", userId);
        postRequest.postRequest(createGroupJSON, createGroupURL, token, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                System.out.println("Create Group " + jsonObject);
                loading.loadingGone(loadingGif, createGroupButton, joinGroupButton);
                displayGroups();
            }
        });
    }

    private void joinGroup() {
        loading.loadingVisible(loadingGif, createGroupButton, joinGroupButton);
        JSONObject joinGroupJSON = new JSONObject();
        joinGroupJSON = jsonManipulator.put(joinGroupJSON, "joinCode", joinGroupEditText.getText().toString());
        joinGroupJSON = jsonManipulator.put(joinGroupJSON, "userId", userId);
        postRequest.postRequest(joinGroupJSON, joinGroupURL, token, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                System.out.println("Join Group " + jsonObject);
                loading.loadingGone(loadingGif, createGroupButton, joinGroupButton);
                displayGroups();
            }
        });
    }

    private void setUserIdToURL() {
        userGroupsURL = MoviesMatchURLS.getUserGroupsURL;
        userId = jsonManipulator.getJSONObjectGetString(account, "userDB", "usrId");
        userGroupsURL += userId;
    }

    private void getUsersInGroup(Group group){
        String getGroupInfoURL = MoviesMatchURLS.getGroupInfoURL + group.getGroupId();
        getRequest.getRequest(getGroupInfoURL, token, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonManipulator.getJSONArrayFromJSONObject(jsonObject, "listUsers").length() >= 2){
                    loading.loadingGone(loadingGif, createGroupButton, joinGroupButton);
                    Bundle bundle = new Bundle();
                    bundle.putString("GroupId", group.getGroupId());
                    bundle.putString("Account", account.toString());
                    SwipeFragment swipeFragment = new SwipeFragment();
                    swipeFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.frame, swipeFragment).commit();
                } else {
                    loading.loadingGone(loadingGif, createGroupButton, joinGroupButton);
                    new AlertDialog.Builder(getContext()).setTitle("No friend in the group").setMessage("Make sure you have a friend in the group to start swiping movies!").show();
                }
            }
        });
    }

    private void init() {
        listViewGroups = binding.listViewGroups;
        createGroupButton = binding.buttonCreateGroup;
        joinGroupButton = binding.buttonJoin;
        createGroupEditText = binding.editTextCreateGroup;
        joinGroupEditText = binding.editTextJoinGroup;
        noGroupsTextView = binding.textViewNoGroups;
        loadingGif = binding.groupsLoadingGif;
        arrayListGroups = new ArrayList<JSONObject>();
        getRequest = new GetRequest((MainActivity) getActivity());
        postRequest = new PostRequest((MainActivity) getActivity());
        jsonManipulator = new JSONManipulator();
        validation = new InputsValidation(getContext());
        loading = new Loading();
        groupFactory = new GroupFactory();
        account = jsonManipulator.newJSONObject(getArguments().getString("Account"));
        setUserIdToURL();
        token = jsonManipulator.getString(account, "token");
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        loading.loadingGone(loadingGif, createGroupButton, joinGroupButton);
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        loading.loadingGone(loadingGif, createGroupButton, joinGroupButton);
        if (errorCode == 400){
            new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("This group does not exist or may be full").show();
        } else {
            new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
        }
    }

    @Override
    public void onBackPressed() {
        exitCount++;
        if (exitCount == 2){
            getActivity().finishAffinity();
            System.exit(0);
        } else {
            Toast.makeText(getActivity(), "Press back again to exit",
                    Toast.LENGTH_LONG).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exitCount = 0;
            }
        }, 2500);
    }
}