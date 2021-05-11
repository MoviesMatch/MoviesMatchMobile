package com.example.moviesmatch.layouts.fragments;

import androidx.annotation.NonNull;
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

import com.example.moviesmatch.R;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentGroupsBinding;
import com.example.moviesmatch.databinding.FragmentMovieInfosBinding;
import com.example.moviesmatch.databinding.FragmentSwipeBinding;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.adapters.GroupsAdapter;
import com.example.moviesmatch.requests.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupsFragment extends Fragment {
    private ListView listViewGroups;
    private FragmentGroupsBinding binding;
    private JSONObject account;
    private ArrayList arrayListGroups;
    private GroupsAdapter adapter;
    private String currentAccount;
    private GetRequest getReq;
    private CertificateByPass certificat;
    private String URL;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(getLayoutInflater());
        currentAccount = getArguments().getString("Account");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        displayGroups();
    }

    private void displayGroups() {
        getGrpUser();

        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object group = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("Group", group.toString());
                bundle.putString("Acc", currentAccount);
                SwipeFragment swipeFragment = new SwipeFragment();
                swipeFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame, swipeFragment).commit();
            }
        });
    }

    private void setUp() {
        listViewGroups = binding.listViewGroups;
        arrayListGroups = new ArrayList<JSONObject>();
        getReq = new GetRequest((MainActivity) getActivity());
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();
    }

    private void setUserGroupURL() {
        URL = "/api/user/getUserGroups/";
        String usrId = "";
        try {
            usrId = account.getJSONObject("userDB").getString("usrId");
            token = account.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        URL += usrId;
    }

    private void getGrpUser() {
        try {
            account = new JSONObject(currentAccount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUserGroupURL();

        getReq.getRequestArray(URL, token, new IRequestCallbackArray() {
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
}