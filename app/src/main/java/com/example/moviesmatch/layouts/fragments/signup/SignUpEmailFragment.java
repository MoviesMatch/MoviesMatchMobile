package com.example.moviesmatch.layouts.fragments.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviesmatch.databinding.FragmentSignUpEmailBinding;
import com.example.moviesmatch.layouts.activities.CreateAccountActivity;
import com.example.moviesmatch.validation.CountryAbbreviation;
import com.example.moviesmatch.validation.InputsValidation;

public class SignUpEmailFragment extends Fragment {
    private FragmentSignUpEmailBinding binding;
    private InputsValidation inputsValidation;
    private CountryAbbreviation countryAbbreviation;
    private EditText email;
    private Spinner country;
    private Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSignUpEmailBinding.inflate(getLayoutInflater());
        setup();
        nextOnClickListener();
        return binding.getRoot();
    }

    private void nextOnClickListener() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputsValidation.validateEmail(email.getText().toString())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Firstname", getArguments().getString("Firstname"));
                    bundle.putString("Lastname", getArguments().getString("Lastname"));
                    bundle.putString("Email", email.getText().toString());
                    bundle.putString("Country", countryAbbreviation.getCountryAbbreviation(country.getSelectedItem().toString()));
                    ((CreateAccountActivity) getActivity()).replaceFrag(new SignUpPasswordFragment(), bundle);
                }
            }
        });
    }

    private void setup() {
        inputsValidation = new InputsValidation(getContext());
        countryAbbreviation = new CountryAbbreviation();
        email = binding.editTextSignupEmail;
        country = binding.spinnerCountry;
        next = binding.buttonSignupNextEmail;
    }
}
