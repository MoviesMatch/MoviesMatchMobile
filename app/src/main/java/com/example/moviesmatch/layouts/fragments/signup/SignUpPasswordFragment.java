package com.example.moviesmatch.layouts.fragments.signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moviesmatch.databinding.FragmentSignUpPasswordBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.layouts.fragments.GenresFragment;
import com.example.moviesmatch.models.MoviesMatchURLS;
import com.example.moviesmatch.requests.PostRequest;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.layouts.activities.CreateAccountActivity;
import com.example.moviesmatch.validation.CountryAbbreviation;
import com.example.moviesmatch.validation.InputsValidation;
import com.example.moviesmatch.validation.PasswordsEye;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

public class SignUpPasswordFragment extends Fragment implements IPostActivity, IOnBackPressed {
    FragmentSignUpPasswordBinding binding;
    PostRequest postRequest;
    TextView step;
    EditText email;
    EditText password;
    EditText confirmedPassword;
    JSONObject jsonAccount;
    InputsValidation inputsValidation;
    CountryAbbreviation countryAbbreviation;
    GifImageView loadingGif;
    Button nextButton;
    ConstraintLayout constraintLayout;
    PasswordsEye passwordsEye;

    private final String URL = MoviesMatchURLS.signupURL;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSignUpPasswordBinding.inflate(getLayoutInflater());
        setup();
        next();
        passwordsEye();
        return binding.getRoot();
    }

    public void next() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loading();
                    jsonAccount.put("usrEmail", email.getText().toString().trim());
                    jsonAccount.put("usrFirstname", getFirstname());
                    jsonAccount.put("usrLastname", getLastname());
                    jsonAccount.put("usrPassword", password.getText().toString());
                    jsonAccount.put("usrCountry", getArguments().getString("Country"));
                    System.out.println(jsonAccount);
                    createAccount();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createAccount() {
        if (inputsValidation.validateEmail(email.getText().toString()) && inputsValidation.validatePassword(password.getText().toString(), confirmedPassword.getText().toString())) {
            postRequest.postRequest(jsonAccount, URL, null, new IRequestCallback() {
                //Called when postRequest is done
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Account", jsonObject.toString());
                    bundle.putString("Parent", "CreateAccountActivity");
                    ((CreateAccountActivity) getActivity()).replaceFrag(new GenresFragment(), bundle);
                    loadingGone();
                }
            });
        } else {
            loadingGone();
        }
    }

    private void passwordsEye() {
        passwordsEye.passwordEye(password);
        passwordsEye.passwordEye(confirmedPassword);
    }

    private void loading() {
        loadingGif.setVisibility(View.VISIBLE);
        nextButton.setEnabled(false);
    }

    private void loadingGone() {
        loadingGif.setVisibility(View.GONE);
        nextButton.setEnabled(true);
    }

    private void setup() {
        loadingGif = binding.signUpLoadingGif;
        postRequest = new PostRequest((CreateAccountActivity) getActivity());
        jsonAccount = new JSONObject();
        inputsValidation = new InputsValidation(getContext());
        countryAbbreviation = new CountryAbbreviation();
        passwordsEye = new PasswordsEye();
        constraintLayout = binding.layoutSignUp;
        step = binding.textViewStep2of3;
        email = binding.editTextSignupEmail;
        password = binding.editTextPassword;
        confirmedPassword = binding.editTextConfirmPassword;
        nextButton = binding.buttonNextPassword;
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        loadingGone();
        if (errorCode == 403) {
            new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("This email is already taken").show();
        } else {
            new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("Firstname", getFirstname());
        bundle.putString("Lastname",getLastname());
        ((CreateAccountActivity)getActivity()).replaceFrag(new SignUpNameFragment(), bundle);
    }

    private String getFirstname(){
        return getArguments().getString("Firstname");
    }
    
    private String getLastname(){
        return getArguments().getString("Lastname");
    }
}