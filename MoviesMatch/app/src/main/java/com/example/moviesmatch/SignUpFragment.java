package com.example.moviesmatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moviesmatch.async.PostRequest;
import com.example.moviesmatch.certificate.CertificateByPass;
import com.example.moviesmatch.databinding.FragmentGenresBinding;
import com.example.moviesmatch.databinding.FragmentSignUpBinding;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.validation.CountryAbbreviation;
import com.example.moviesmatch.validation.InputsValidation;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

public class SignUpFragment extends Fragment implements IPostActivity {
    FragmentSignUpBinding binding;
    PostRequest postRequest;
    EditText firstName;
    EditText lastName;
    EditText email;
    Spinner country;
    EditText password;
    EditText confirmedPassword;
    JSONObject jsonAccount;
    InputsValidation inputsValidation;
    CountryAbbreviation countryAbbreviation;
    CertificateByPass certificateByPass;
    GifImageView loadingGif;
    Button nextButton;
    ConstraintLayout constraintLayout;

    private final String URL = "/api/user/signUp";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSignUpBinding.inflate(getLayoutInflater());
        setup();
        next();
        return binding.getRoot();
    }

    public void next() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loadingGif.setVisibility(View.VISIBLE);
                    nextButton.setEnabled(false);
                    jsonAccount.put("usrEmail", email.getText().toString());
                    jsonAccount.put("usrFirstname", firstName.getText().toString());
                    jsonAccount.put("usrLastname", lastName.getText().toString());
                    jsonAccount.put("usrPassword", password.getText().toString());
                    jsonAccount.put("usrCountry", countryAbbreviation.getCountryAbbreviation(country.getSelectedItem().toString()));
                    System.out.println(jsonAccount);
                    createAccount();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createAccount() {
        if (inputsValidation.validateName(firstName.getText().toString(), lastName.getText().toString())
                && inputsValidation.validateEmail(email.getText().toString())
                && inputsValidation.validatePassword(password.getText().toString(), confirmedPassword.getText().toString())) {

            postRequest.postRequest(jsonAccount, URL, new IRequestCallback() {
                //Called when postRequest is done
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    ((CreateAccountActivity)getActivity()).replaceFrag(new GenresFragment(), jsonObject.toString());
                    loadingGif.setVisibility(View.GONE);
                    nextButton.setEnabled(true);
                }
            });
        } else {
            loadingGif.setVisibility(View.GONE);
            nextButton.setEnabled(true);
        }
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        loadingGif.setVisibility(View.GONE);
        nextButton.setEnabled(true);
        if (errorCode == 403){
            new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("This email is already taken").show();
        } else {
            new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Please try again").show();
        }
    }

    private void setup(){
        certificateByPass = new CertificateByPass();
        certificateByPass.IngoreCertificate();
        postRequest = new PostRequest((CreateAccountActivity)getActivity());
        firstName = binding.editTextFirstName;
        lastName = binding.editTextLastName;
        email = binding.editTextEmail;
        country = binding.spinnerCountry;
        password = binding.editTextPassword;
        confirmedPassword = binding.editTextConfirmPassword;
        jsonAccount = new JSONObject();
        inputsValidation = new InputsValidation(getContext());
        countryAbbreviation = new CountryAbbreviation();
        loadingGif = binding.signUpLoadingGif;
        nextButton = binding.buttonRegister;
        constraintLayout = binding.layoutSignUp;
    }
}