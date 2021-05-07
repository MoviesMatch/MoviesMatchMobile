package com.example.moviesmatch.layouts.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.moviesmatch.R;
import com.example.moviesmatch.layouts.fragments.SignUpFragment;
import com.example.moviesmatch.databinding.ActivityCreateAccountBinding;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.validation.OnErrorResponse;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity implements IPostActivity, IGetActivity {
    private ActivityCreateAccountBinding binding;
    private OnErrorResponse onErrorResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        onErrorResponse = new OnErrorResponse();
        setContentView(view);

        //Fragment signUpFragment opened when CreateAccountActivity is called
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameCreateAccountActivity,
                    new SignUpFragment()).commit();
        }
    }

    public boolean replaceFrag(Fragment frag, Bundle infoToPass){
        if (frag != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            frag.setArguments(infoToPass);
            transaction.replace(R.id.frameCreateAccountActivity, frag);
            transaction.commit();
            return true;
        }
        return false;
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        onErrorResponse.onPostErrorResponse(errorCode, this);
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        onErrorResponse.onGetErrorResponse(errorCode, this);
    }
}