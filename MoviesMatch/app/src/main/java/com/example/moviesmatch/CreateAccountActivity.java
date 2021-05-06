package com.example.moviesmatch;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.moviesmatch.databinding.ActivityCreateAccountBinding;
import com.example.moviesmatch.interfaces.IPostActivity;

import pl.droidsonroids.gif.GifImageView;

public class CreateAccountActivity extends AppCompatActivity implements IPostActivity {
    private ActivityCreateAccountBinding binding;
    private SignUpFragment signUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Fragment signUpFragment opened when CreateAccountActivity is called
        if (savedInstanceState == null) {
            signUpFragment = new SignUpFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameCreateAccountActivity,
                    signUpFragment).commit();
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
        signUpFragment.onErrorResponse();
        if (errorCode == 403){
            new AlertDialog.Builder(this).setTitle("Error").setMessage("This email is already taken").show();
        } else {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Please try again").show();
        }
    }
}