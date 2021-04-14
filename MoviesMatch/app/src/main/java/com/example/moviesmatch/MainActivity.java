package com.example.moviesmatch;

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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
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
                Fragment frag = null;
                int itemId = menuItem.getItemId();
                if (itemId == R.id.account) {
                    frag = new AccountFragment();
                } else if (itemId == R.id.settings) {
                    frag = new SettingsFragment();
                } else if (itemId == R.id.donate) {
                    frag = new DonateFragment();
                } else if (itemId == R.id.groups) {
                    frag = new GroupsFragment();
                } else if (itemId == R.id.disconnect) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, frag);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }
}