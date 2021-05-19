package com.example.moviesmatch.layouts.fragments.account;

import android.os.Bundle;
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
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.requests.PutRequest;
import com.example.moviesmatch.validation.CountryAbbreviation;
import com.example.moviesmatch.validation.InputsValidation;
import com.example.moviesmatch.validation.JSONManipulator;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private Button buttonChangePassword, buttonSave;
    private EditText editTextFirstName, editTextLastName;
    private TextView textViewEmail;
    private Spinner spinnerCountry;
    private JSONManipulator jsonManipulator;
    private JSONObject account,updAccount;
    private CountryAbbreviation countryAbbreviation;
    private PutRequest putRequest;
    private InputsValidation inputsValidation;
    private String URL = "/api/user/updateUser";
    private String token ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      // refresh();
        setUp();
        fillInfos();
        goToChangePassword();
        saveChanges();
    }

    private void setUp() {
        editTextFirstName = binding.editTextFirstName;
        textViewEmail = binding.textViewEmail;
        editTextLastName = binding.editTextLastName;
        spinnerCountry = binding.spinnerCountry;
        buttonChangePassword = binding.buttonChangePassword;
        buttonSave = binding.buttonSave;
        updAccount = new JSONObject();
        countryAbbreviation = new CountryAbbreviation();
        jsonManipulator = new JSONManipulator();
        account = jsonManipulator.newJSONObject(getArguments().getString("Account"));
        putRequest = new PutRequest((AppCompatActivity) getActivity());
        inputsValidation = new InputsValidation(getContext());
        token =  jsonManipulator.getString(account, "token");
    }

    private void fillInfos() {
        System.out.println(account);
        editTextFirstName.setText(jsonManipulator.getJSONObjectGetString(account, "userDB", "usrFirstname"));
        editTextLastName.setText(jsonManipulator.getJSONObjectGetString(account, "userDB", "usrLastname"));
        textViewEmail.setText(jsonManipulator.getJSONObjectGetString(account, "userDB", "usrEmail"));

        // textViewCountry.setText(countryAbbreviation.getCountryAbbreviationReverse(jsonManipulator.getJSONObjectGetString(account,"userDB","usrCountry")));
    }


    /*private void refresh(){
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.account);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }*/

    private void saveChanges() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputsValidation.validateName(editTextFirstName.getText().toString(), editTextLastName.getText().toString())) {
                    try {
                        updAccount.put("usrId", jsonManipulator.getJSONObjectGetString(account,"userDB","usrId"));
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
        putRequest.putRequest(updAccount, URL, token, new IRequestCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
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
}
