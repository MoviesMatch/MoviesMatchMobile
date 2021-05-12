package com.example.moviesmatch.layouts.fragments.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviesmatch.R;
import com.example.moviesmatch.databinding.FragmentAccountBinding;
import com.example.moviesmatch.validation.CountryAbbreviation;
import com.example.moviesmatch.validation.JSONManipulator;

import org.json.JSONObject;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private Button buttonChangePassword;
    private TextView textViewFullName, textViewEmail, textViewCountry;
    private JSONManipulator jsonManipulator;
    private JSONObject account;
    private CountryAbbreviation countryAbbreviation;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        fillInfos();
        goToChangePassword();
    }

    private void setUp() {
        textViewFullName = binding.textViewFullName;
        textViewEmail = binding.textViewEmail;
        textViewCountry = binding.textViewCountry;
        buttonChangePassword = binding.buttonChangePassword;
        countryAbbreviation = new CountryAbbreviation();
        jsonManipulator = new JSONManipulator();
        account = jsonManipulator.newJSONObject(getArguments().getString("Account"));
    }

    private void fillInfos(){
        System.out.println(account);
        textViewFullName.setText(jsonManipulator.getJSONObjectGetString(account,"userDB","usrFirstname") + " " + jsonManipulator.getJSONObjectGetString(account,"userDB","usrLastname"));
        textViewEmail.setText(jsonManipulator.getJSONObjectGetString(account,"userDB","usrEmail"));
        textViewCountry.setText(countryAbbreviation.getCountryAbbreviationReverse(jsonManipulator.getJSONObjectGetString(account,"userDB","usrCountry")));
    }


    private void goToChangePassword(){
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountPasswordFragment fragment = new AccountPasswordFragment();
                Bundle bundle = new Bundle();
                bundle.putString("account",account.toString());
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
            }
        });
    }
}
