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
    private  ArrayList arrayListGroups;
    private GroupsAdapter adapter;


    private GetRequest getReq;
    CertificateByPass certificat;
    static String URL = "/api/movie/GetUserGroups";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setUp();
        displayGroups();
    }

    private void displayGroups(){
        getGrpUser();
        adapter = new GroupsAdapter(getContext(), arrayListGroups);
        listViewGroups.setAdapter(adapter);
        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object group = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("currentGroup",group.toString());
                SwipeFragment swipeFragment = new SwipeFragment();
                swipeFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame, swipeFragment).commit();

            }
        });

    }

    private void onClickGroup() {
      /*  arrayListGroups = new ArrayList<>(Arrays.asList("GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3", "GROUPE 1", "GROUPE 2", "GROUPE 3"));
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
        });*/
    }

    private void setUp(){
        listViewGroups = binding.listViewGroups;
        arrayListGroups = new ArrayList<JSONObject>();
        getReq = new GetRequest((MainActivity) getActivity());
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();
    }

    private void setUserGenreURL() {
        String usrId = "";
        try {
            usrId = account.getString("usrId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        URL += "?idUser=" + usrId;
    }

    private void getGrpUser(){
        try {
            System.out.println(getArguments().getString("Account"));
            account = new JSONObject(getArguments().getString("Account"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setUserGenreURL();

        getReq.getRequest(URL, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("$values");

                    for(int i = 0; i < jsonArray.length(); i++){
                        arrayListGroups.add(jsonArray.getJSONObject(i));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}