package com.example.moviesmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.moviesmatch.interfaces.IGetActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IGetActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView imageMatch;
    private String account;
    private Fragment frag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        imageMatch = findViewById(R.id.imageMatch);
        account = getIntent().getStringExtra("Account");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navSelectedItem();
        toolbarIconClicked();

        //Fragment groups_fragment opened when MainActivity is called
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                    new GroupsFragment()).commit();
            navigationView.setCheckedItem(R.id.groups);
        }
    }

    //Event when clicked on hamburger icon
    private void toolbarIconClicked() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    //Events when clicked on the hamburger menu items to open fragments
    private void navSelectedItem() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                imageMatch.setVisibility(View.GONE);
                int itemId = menuItem.getItemId();
                switch (itemId) {
                    case R.id.groups:
                        frag = new GroupsFragment();
                        break;
                    case R.id.account:
                        frag = new AccountFragment();
                        break;
                    case R.id.genres:
                        frag = new GenresFragment();
                        break;
                    case R.id.settings:
                        frag = new SettingsFragment();
                        break;
                    case R.id.donate:
                        frag = new DonateFragment();
                        break;
                    case R.id.disconnect:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                }
                return replaceFrag(frag);
            }
        });
    }

    public boolean replaceFrag(Fragment frag) {
        if (frag != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle accountBundle = new Bundle();
            accountBundle.putString("Account", account);
            accountBundle.putString("Parent", "MainActivity");
            frag.setArguments(accountBundle);
            transaction.replace(R.id.frame, frag);
            transaction.commit();
            drawerLayout.closeDrawers();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        tellFragments();
    }

    private void tellFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment f : fragments) {
            if (f != null && f instanceof SwipeFragment)
                ((SwipeFragment) f).onBackPressed();
        }
    }

    public void matchFragment(View view) {
        replaceFrag(new MatchFragment());
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        if (frag instanceof GenresFragment) {
            ((GenresFragment) frag).loadingGone();
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Make sure you are connected to an internet connection and try again").show();
        }
    }
}