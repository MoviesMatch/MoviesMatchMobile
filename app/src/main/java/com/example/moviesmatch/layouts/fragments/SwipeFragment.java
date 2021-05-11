package com.example.moviesmatch.layouts.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moviesmatch.R;
import com.example.moviesmatch.interfaces.IRequestCallbackArray;
import com.example.moviesmatch.layouts.adapters.SwipeAdapter;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentSwipeBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SwipeFragment extends Fragment implements IOnBackPressed {
    private SwipeAdapter arrayAdapter;
    private SwipeFlingAdapterView flingContainer;
    private Button buttonLeft, buttonRight;
    private GetRequest getReq;
    private ArrayList<JSONObject> listJsonObjectsFilms;
    private FragmentSwipeBinding binding;
    private CertificateByPass certificat;
    private String URL;
    private String currentAccount;
    private String currentGroup;
    private JSONObject account;
    private JSONObject group;
    private String token;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSwipeBinding.inflate(getLayoutInflater());
        currentAccount = getArguments().getString("Account");
        currentGroup = getArguments().getString("Group");
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    public void swipeButtons() {
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectLeft();
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });


    }

    private void getRequestListFilm() {
        try {
            account = new JSONObject(currentAccount);
            group = new JSONObject(currentGroup);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUserSwipeURL();

        getReq.getRequestArray(URL, token,new IRequestCallbackArray() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        listJsonObjectsFilms.add(jsonArray.getJSONObject(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fling();
            }
        });
    }


    private void setUserSwipeURL() {
        URL = "/api/movie/GetMovies";
        String usrId = "";
        String grpId = "";
        try {
            usrId = "&userId=" + account.getJSONObject("userDB").getString("usrId");
            grpId = "?groupId="+group.getString("grpId");
            token = account.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        URL += grpId + usrId;
    }


    private void fling() {
        arrayAdapter = new SwipeAdapter(getContext(), listJsonObjectsFilms);
        flingContainer.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                listJsonObjectsFilms.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }

            @Override
            public void onRightCardExit(Object dataObject) {
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
            }

            @Override
            public void onScroll(float v) {
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int i, Object o) {
                MovieInfosFragment movieInfosFragment = new MovieInfosFragment();

                Bundle bundle = new Bundle();
                bundle.putString("movie", o.toString());
                movieInfosFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.frame, movieInfosFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ((MainActivity) getActivity()).replaceFrag(new GroupsFragment());
    }

    public void setUp() {
        buttonLeft = binding.buttonLeft;
        buttonRight = binding.buttonRight;
        flingContainer = binding.frame;

        listJsonObjectsFilms = new ArrayList<>();
        getReq = new GetRequest((MainActivity) getActivity());
        certificat = new CertificateByPass();
        certificat.IngoreCertificate();

        getRequestListFilm();
        swipeButtons();
    }

}