package com.example.moviesmatch.layouts.activities;

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

import com.example.moviesmatch.interfaces.IOnClickMatchListener;
import com.example.moviesmatch.interfaces.IPostActivity;
import com.example.moviesmatch.interfaces.IPutActivity;
import com.example.moviesmatch.layouts.fragments.DonateFragment;
import com.example.moviesmatch.layouts.fragments.GenresFragment;
import com.example.moviesmatch.layouts.fragments.GroupsFragment;
import com.example.moviesmatch.layouts.fragments.MatchFragment;
import com.example.moviesmatch.R;
import com.example.moviesmatch.layouts.fragments.MovieInfosFragment;
import com.example.moviesmatch.layouts.fragments.SettingsFragment;
import com.example.moviesmatch.layouts.fragments.SwipeFragment;
import com.example.moviesmatch.interfaces.IGetActivity;
import com.example.moviesmatch.layouts.fragments.account.AccountFragment;

import com.example.moviesmatch.models.Movie;

import com.example.moviesmatch.layouts.fragments.account.AccountPasswordFragment;

import com.example.moviesmatch.validation.OnErrorResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IGetActivity, IPostActivity, IPutActivity, IOnClickMatchListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView imageMatch;
    private String account;
    private Fragment frag = null;
    private OnErrorResponse onErrorResponse;

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
        onErrorResponse = new OnErrorResponse();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navSelectedItem();
        toolbarIconClicked();

        //Fragment groups_fragment opened when MainActivity is called
        if (savedInstanceState == null) {
            replaceFrag(new GroupsFragment());
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
            if (f != null && f instanceof SwipeFragment) {
                ((SwipeFragment) f).onBackPressed();
            } else if (f != null && f instanceof MovieInfosFragment) {
                ((MovieInfosFragment) f).onBackPressed();
            } else if (f != null && f instanceof GroupsFragment) {
                ((GroupsFragment) f).onBackPressed();
            } else if (f != null && f instanceof MatchFragment) {
                ((MatchFragment) f).onBackPressed();
            } else if (f != null && f instanceof AccountFragment) {
                ((AccountFragment) f).onBackPressed();
            } else if (f != null && f instanceof AccountPasswordFragment) {
                ((AccountPasswordFragment) f).onBackPressed();
            } else if (f != null && f instanceof GenresFragment) {
                ((GenresFragment) f).onBackPressed();
            }
        }
    }

    public void imageMatchVisible(){
        imageMatch.setVisibility(View.VISIBLE);
    }

    public void imageMatchClick(String token, String groupId){
        imageMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageMatch.setVisibility(View.GONE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                MatchFragment matchFragment = new MatchFragment();
                Bundle bundle = new Bundle();
                bundle.putString("GroupId", groupId);
                bundle.putString("Token", token);
                matchFragment.setArguments(bundle);
                transaction.replace(R.id.frame, matchFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void matchFragmentFromListMatch(Movie o){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MovieInfosFragment movieInfosFragment = new MovieInfosFragment();
        Bundle bundle = new Bundle();
        Movie movie = (Movie) o;
        bundle.putParcelable("Movie", movie);
        movieInfosFragment.setArguments(bundle);
        transaction.replace(R.id.frame, movieInfosFragment).addToBackStack(null).commit();
    }

    public void matchFragment(View view) {
        replaceFrag(new MatchFragment());
    }

    @Override
    public void onGetErrorResponse(int errorCode) {
        onErrorResponse.onGetErrorResponse(errorCode, this);
    }

    @Override
    public void onPostErrorResponse(int errorCode) {
        onErrorResponse.onPostErrorResponse(errorCode, this);
    }

    @Override
    public void onPutErrorResponse(int errorCode) {
        onErrorResponse.onPutErrorResponse(errorCode, this);
    }
}