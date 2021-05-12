package com.example.moviesmatch.validation;

import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class PasswordsEye {
    Boolean isPasswordHidden;

    public void passwordEye(EditText passwordEditText){
        isPasswordHidden = true;
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (isPasswordHidden){
                            passwordEditText.setTransformationMethod(null);
                            isPasswordHidden = false;
                        } else {
                            passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                            isPasswordHidden = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
