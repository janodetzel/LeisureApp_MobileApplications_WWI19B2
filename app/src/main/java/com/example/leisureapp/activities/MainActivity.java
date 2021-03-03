package com.example.leisureapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;

import com.example.leisureapp.FavoritesFragment;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.fragments.HomeFragment;
import com.example.leisureapp.R;
import com.example.leisureapp.fragments.SettingsFragment;

import org.jetbrains.annotations.NotNull;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Database
        DatabaseManager _databaseManager = new DatabaseManager(this);

        animatedBottomBar = findViewById(R.id.bottom_bar);

        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.tab_home, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        }

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @org.jetbrains.annotations.Nullable AnimatedBottomBar.Tab lastTab, int nextIndex, @NotNull AnimatedBottomBar.Tab nextTab) {
                Fragment fragment = null;
                switch (nextTab.getId()) {
                    case R.id.tab_favorites:
                        fragment = new FavoritesFragment();
                        break;
                    case R.id.tab_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.tab_settings:
                        fragment = new SettingsFragment();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                } else {
                    Log.e(TAG, "Error in creating Fragment");
                }
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });
    }
}