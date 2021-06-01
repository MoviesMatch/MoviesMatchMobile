package com.example.moviesmatch.layouts.fragments.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.moviesmatch.R;
import com.example.moviesmatch.databinding.FragmentAccountBinding;
import com.example.moviesmatch.databinding.FragmentAccountPasswordBinding;
import com.example.moviesmatch.interfaces.IOnBackPressed;
import com.example.moviesmatch.interfaces.IPutActivity;
import com.example.moviesmatch.interfaces.IRequestCallback;
import com.example.moviesmatch.layouts.activities.MainActivity;
import com.example.moviesmatch.layouts.fragments.GroupsFragment;
import com.example.moviesmatch.models.MoviesMatchURLS;
import com.example.moviesmatch.requests.PutRequest;
import com.example.moviesmatch.validation.InputsValidation;
import com.example.moviesmatch.validation.JSONManipulator;
import com.example.moviesmatch.validation.PasswordsEye;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountPasswordFragment extends Fragment implements IOnBackPressed, IPutActivity {

    private FragmentAccountPasswordBinding binding;
    private EditText oldPassword, newPassword, confirmNewPassword;
    private Button buttonSavePassword;
    private PasswordsEye passwordsEye;
    private PutRequest putRequest;
    private String URL = MoviesMatchURLS.changePasswordURL;
    private JSONObject account, updAccount;
    private String token;
    private JSONManipulator jsonManipulator;
    private InputsValidation inputsValidation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountPasswordBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
        passwordsEye();
        savePassword();
    }

    private void passwordsEye() {
        passwordsEye.passwordEye(oldPassword);
        passwordsEye.passwordEye(newPassword);
        passwordsEye.passwordEye(confirmNewPassword);
    }

    private void setUp() {
        oldPassword = binding.editTextOldPassword;
        newPassword = binding.editTextNewPassword;
        confirmNewPassword = binding.editTextConfirmNewPassword;
        buttonSavePassword = binding.buttonSavePassword;
        passwordsEye = new PasswordsEye();
        updAccount = new JSONObject();
        jsonManipulator = new JSONManipulator();
        account = jsonManipulator.newJSONObject(getArguments().getString("Account"));
        putRequest = new PutRequest((AppCompatActivity) getActivity());
        inputsValidation = new InputsValidation(getContext());
        token = jsonManipulator.getString(account, "token");
    }

    private void savePassword() {
        buttonSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputsValidation.validatePassword(newPassword.getText().toString(), confirmNewPassword.getText().toString())) {
                    try {
                        updAccount.put("id", jsonManipulator.getJSONObjectGetString(account, "userDB", "usrId"));
                        updAccount.put("oldPassword", oldPassword.getText().toString());
                        updAccount.put("newPassword", newPassword.getText().toString());
                        System.out.println(updAccount);
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
                new AlertDialog.Builder(getContext()).setTitle("Success!").setMessage("Your password has been changed.").show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        ((MainActivity) getActivity()).replaceFrag(new AccountFragment());
    }

    @Override
    public void onPutErrorResponse(int errorCode) {
            if (errorCode == 401) {
                new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Please make sure you've written the right password.").show();
            } else {
                new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
            }
    }
}