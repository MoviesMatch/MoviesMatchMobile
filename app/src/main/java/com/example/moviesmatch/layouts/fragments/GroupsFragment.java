package com.example.moviesmatch.layouts.fragments;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.moviesmatch.R;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentGroupsBinding;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.adapters.GroupsAdapter;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.validation.JSONArrayManipulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupsFragment extends Fragment {
    private ListView listViewGroups;
    private FragmentGroupsBinding binding;
    private JSONObject account;
    private ArrayList arrayListGroups;
    private GroupsAdapter adapter;
    private GetRequest getRequest;
    private CertificateByPass certificat;
    private String userGroupsURL = "/api/user/getUserGroups/";
    private String token;
    private String userId;
    private JSONArrayManipulator jsonArrayManipulator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(getLayoutInflater());
        init();
        displayGroups();
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

    private void setUserIdToURL() {
        userId = jsonArrayManipulator.getJSONObjectString(account, "userDB", "usrId");
        userGroupsURL += userId;
    }

    private void init() {
        listViewGroups = binding.listViewGroups;
        arrayListGroups = new ArrayList<JSONObject>();
        getRequest = new GetRequest((MainActivity) getActivity());
        jsonArrayManipulator = new JSONArrayManipulator();
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();
        account = jsonArrayManipulator.setJSONObject(getArguments().getString("Account"));
        setUserIdToURL();
        token = jsonArrayManipulator.getJSONString(account, "token");
    }
}