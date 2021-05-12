package com.example.moviesmatch.layouts.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.moviesmatch.R;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentGroupsBinding;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.adapters.GroupsAdapter;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.requests.PostRequest;
import com.example.moviesmatch.validation.JSONArrayManipulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupsFragment extends Fragment implements IGetActivity, IPostActivity {
    private ListView listViewGroups;
    private FragmentGroupsBinding binding;
    private JSONObject account;
    private ArrayList arrayListGroups;
    private GroupsAdapter adapter;
    private GetRequest getRequest;
    private PostRequest postRequest;
    private CertificateByPass certificat;
    private String userGroupsURL = "/api/user/getUserGroups/";
    private String createGroupURL = "/api/group/createGroup";
    private String token;
    private String userId;
    private JSONArrayManipulator jsonArrayManipulator;
    private Button createGroupButton;
    private EditText createGroupEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(getLayoutInflater());
        init();
        displayGroups();
        createGroupButtonOnClick();
        return binding.getRoot();
    }

    private void displayGroups() {
        getGroupUser();

        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object group = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("Group", group.toString());
                bundle.putString("Account", account.toString());
                SwipeFragment swipeFragment = new SwipeFragment();
                swipeFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame, swipeFragment).commit();
            }
        });
    }

    private void getGroupUser() {
        getRequest.getRequestArray(userGroupsURL, token, new IRequestCallbackArray() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayListGroups.add(jsonArray.getJSONObject(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new GroupsAdapter(getContext(), arrayListGroups);
                listViewGroups.setAdapter(adapter);
            }
        });
    }

    private void createGroupButtonOnClick(){
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroup();
            }
        });
    }

    private void createGroup(){
        JSONObject createGroupJSON = new JSONObject();
        createGroupJSON = jsonArrayManipulator.putJSONObject(createGroupJSON, "groupName", createGroupEditText.getText().toString());
        createGroupJSON = jsonArrayManipulator.putJSONObject(createGroupJSON, "userId", userId);
        postRequest.postRequest(createGroupJSON, createGroupURL, token, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                System.out.println("Create Group " + jsonObject);
                displayGroups();
            }
        });
    }

    private void setUserIdToURL() {
        userId = jsonArrayManipulator.getJSONObjectString(account, "userDB", "usrId");
        userGroupsURL += userId;
    }

    private void init() {
        listViewGroups = binding.listViewGroups;
        createGroupButton = binding.buttonCreateGroup;
        createGroupEditText = binding.editTextCreateGroup;
        arrayListGroups = new ArrayList<JSONObject>();
        getRequest = new GetRequest((MainActivity) getActivity());
        postRequest = new PostRequest((MainActivity)getActivity());
        jsonArrayManipulator = new JSONArrayManipulator();
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();
        account = jsonArrayManipulator.setJSONObject(getArguments().getString("Account"));
        setUserIdToURL();
        token = jsonArrayManipulator.getJSONString(account, "token");
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
    }
}