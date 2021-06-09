package com.example.moviesmatch.layouts.fragments.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviesmatch.databinding.FragmentSignUpNameBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.layouts.activities.CreateAccountActivity;
import com.example.moviesmatch.layouts.activities.LoginActivity;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.validation.CountryAbbreviation;
import com.example.moviesmatch.validation.InputsValidation;

public class SignUpNameFragment extends Fragment implements IOnBackPressed {
    private FragmentSignUpNameBinding binding;
    private InputsValidation inputsValidation;
    private CountryAbbreviation countryAbbreviation;
    private TextView step;
    private EditText firstname;
    private EditText lastname;
    private Spinner country;
    private Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSignUpNameBinding.inflate(getLayoutInflater());
        setup();
        nextOnClickListener();
        return binding.getRoot();
    }

    private void nextOnClickListener() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputsValidation.validateName(firstname.getText().toString(), lastname.getText().toString())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Firstname", firstname.getText().toString());
                    bundle.putString("Lastname", lastname.getText().toString());
                    bundle.putString("Country", countryAbbreviation.getCountryAbbreviation(country.getSelectedItem().toString()));
                    ((CreateAccountActivity) getActivity()).replaceFrag(new SignUpPasswordFragment(), bundle);
                }
            }
        });
    }

    private void setup() {
        inputsValidation = new InputsValidation(getContext());
        countryAbbreviation = new CountryAbbreviation();
        step = binding.textViewStep1of3;
        firstname = binding.editTextSignupFirstName;
        lastname = binding.editTextSignupLastName;
        country = binding.spinnerCountry;
        next = binding.buttonSignupNextName;
        if (getArguments() != null){
            firstname.setText(getArguments().getString("Firstname"));
            lastname.setText(getArguments().getString("Lastname"));
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
