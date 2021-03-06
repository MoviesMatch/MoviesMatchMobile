package com.example.moviesmatch.layouts.fragments.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.moviesmatch.R;
import com.example.moviesmatch.databinding.FragmentAccountBinding;
import com.example.moviesmatch.interfaces.IDeleteActivity;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.layouts.activities.LoginActivity;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.activities.ResetPasswordActivity;
import com.example.moviesmatch.layouts.fragments.GroupsFragment;
import com.example.moviesmatch.models.MoviesMatchURLS;
import com.example.moviesmatch.requests.DeleteRequest;
import com.example.moviesmatch.requests.GetRequest;
import com.example.moviesmatch.requests.PutRequest;
import com.example.moviesmatch.validation.CountryAbbreviation;
import com.example.moviesmatch.validation.InputsValidation;
import com.example.moviesmatch.validation.JSONManipulator;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountFragment extends Fragment implements IOnBackPressed, IDeleteActivity {

    private FragmentAccountBinding binding;
    private Button buttonChangePassword, buttonSave, buttonDeleteUser;
    private EditText editTextFirstName, editTextLastName;
    private TextView textViewEmail;
    private Spinner spinnerCountry;
    private JSONManipulator jsonManipulator;
    private JSONObject account, updAccount;
    private CountryAbbreviation countryAbbreviation;
    private PutRequest putRequest;
    private GetRequest getRequest;
    private DeleteRequest deleteRequest;
    private InputsValidation inputsValidation;
    private String URLUpdate = MoviesMatchURLS.updateUserURL;
    private String URLGetAccount;
    private String URLDeleteAccount;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        getAccountById();
        goToChangePassword();
        saveChanges();
        deleteUser();
    }

    private void setUp() {
        editTextFirstName = binding.editTextFirstName;
        textViewEmail = binding.textViewEmail;
        editTextLastName = binding.editTextLastName;
        spinnerCountry = binding.spinnerCountry;
        buttonChangePassword = binding.buttonChangePassword;
        buttonSave = binding.buttonSave;
        buttonDeleteUser = binding.buttonDeleteUser;
        updAccount = new JSONObject();
        countryAbbreviation = new CountryAbbreviation();
        jsonManipulator = new JSONManipulator();
        account = jsonManipulator.newJSONObject(getArguments().getString("Account"));
        putRequest = new PutRequest((AppCompatActivity) getActivity());
        getRequest = new GetRequest((AppCompatActivity) getActivity());
        deleteRequest = new DeleteRequest((AppCompatActivity) getActivity());
        inputsValidation = new InputsValidation(getContext());
        token = jsonManipulator.getString(account, "token");
    }

    private void setAccountURL() {
        URLGetAccount = MoviesMatchURLS.getUserURL;
        String usrId = "";
        try {
            usrId = account.getJSONObject("userDB").getString("usrId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        URLGetAccount += usrId;
    }

    private void getAccountById() {
        setAccountURL();
        getRequest.getRequest(URLGetAccount, token, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                editTextFirstName.setText(jsonManipulator.getString(jsonObject, "usrFirstname"));
                editTextLastName.setText(jsonManipulator.getString(jsonObject, "usrLastname"));
                textViewEmail.setText(jsonManipulator.getString(jsonObject, "usrEmail"));
            }
        });
    }

    private void saveChanges() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputsValidation.validateName(editTextFirstName.getText().toString(), editTextLastName.getText().toString())) {
                    try {
                        updAccount.put("usrId", jsonManipulator.getJSONObjectGetString(account, "userDB", "usrId"));
                        updAccount.put("usrFirstname", editTextFirstName.getText().toString());
                        updAccount.put("usrLastname", editTextLastName.getText().toString());
                        updAccount.put("usrCountry", countryAbbreviation.getCountryAbbreviation(spinnerCountry.getSelectedItem().toString()));
                        updateAccount();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void updateAccount() {
        putRequest.putRequest(updAccount, URLUpdate, token, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                editTextFirstName.clearFocus();
                editTextLastName.clearFocus();
                new AlertDialog.Builder(getContext()).setTitle("Success!").setMessage("Your infos has been saved.").show();
            }
        });
    }

    private void goToChangePassword() {
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountPasswordFragment fragment = new AccountPasswordFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Account", account.toString());
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
            }
        });
    }

    private void deleteUser() {
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Delete my account definetly ");
                builder.setMessage("Please enter your password in order to delete your account: ");


                // Set up the input
                final EditText input = new EditText(getContext());

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);


                URLDeleteAccount = MoviesMatchURLS.deleteUserURL;


                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        URLDeleteAccount += "?idUser=" + jsonManipulator.getJSONObjectGetString(account, "userDB","usrId") + "&password=" + input.getText().toString();
                        deleteRequest.deleteRequest(URLDeleteAccount, token, new IRequestCallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                startActivity(new Intent((MainActivity)getActivity(), LoginActivity.class));
                            }
                        });
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        ((MainActivity) getActivity()).replaceFrag(new GroupsFragment());
    }

    @Override
    public void onDeleteErrorResponse(int errorCode) {
        if(errorCode == 401 || errorCode == 400){
            new AlertDialog.Builder(getContext()).setTitle("Delete failed").setMessage("Wrong password").show();
        } else {
            new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
        }
    }
}
