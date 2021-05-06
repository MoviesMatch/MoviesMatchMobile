package com.example.moviesmatch;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.moviesmatch.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Fragment signUpFragment opened when CreateAccountActivity is called
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameCreateAccountActivity,
                    new SignUpFragment()).commit();
        }
    }

    public boolean replaceFrag(Fragment frag, String account){
        if (frag != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("Account", account);
            frag.setArguments(bundle);
            transaction.replace(R.id.frameCreateAccountActivity, frag);
            transaction.commit();
            return true;
        }
        return false;
    }
}