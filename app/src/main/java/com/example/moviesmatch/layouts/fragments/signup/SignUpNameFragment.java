package com.example.moviesmatch.layouts.fragments.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviesmatch.databinding.FragmentSignUpNameBinding;
import com.example.moviesmatch.layouts.activities.CreateAccountActivity;
import com.example.moviesmatch.validation.InputsValidation;

public class SignUpNameFragment extends Fragment {
    private FragmentSignUpNameBinding binding;
    private InputsValidation inputsValidation;
    private EditText firstname;
    private EditText lastname;
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
                    ((CreateAccountActivity) getActivity()).replaceFrag(new SignUpEmailFragment(), bundle);
                }
            }
        });
    }

    private void setup() {
        inputsValidation = new InputsValidation(getContext());
        firstname = binding.editTextSignupFirstName;
        lastname = binding.editTextSignupLastName;
        next = binding.buttonSignupNextName;
    }
}
