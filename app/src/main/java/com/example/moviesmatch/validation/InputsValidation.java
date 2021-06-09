package com.example.moviesmatch.validation;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputsValidation {
    Context context;

    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public InputsValidation(Context context) {
        this.context = context;
    }

    public boolean validateName(String firstName, String lastName) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            new AlertDialog.Builder(context).setTitle("Name").setMessage("First name and last name must not be empty").show();
            return false;
        }
        return true;
    }

    public boolean validateInputNotEmpty(String input, String title, String message) {
        if (input.isEmpty()) {
            new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
            return false;
        }
        return true;
    }

    public boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.find()) {
            new AlertDialog.Builder(context).setTitle("Email").setMessage("Email is not valid").show();
            return false;
        }
        return true;
    }

    public boolean validatePassword(String password, String confirmedPassword) {
        if (password.equals(confirmedPassword)) {
            if (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
                return true;
            } else {
                new AlertDialog.Builder(context).setTitle("Password policy")
                        .setMessage("Passwords must contain at least a lower case, an uppser case, a number and 8 characters").show();
                return false;
            }
        } else {
            new AlertDialog.Builder(context).setTitle("Oups").setMessage("Passwords do not match!").show();
            return false;
        }
    }
}
