package com.example.leisureapp.activities;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.fragments.FavoritesFragment;
import com.example.leisureapp.fragments.HomeFragment;
import com.example.leisureapp.fragments.SettingsFragment;
import com.example.leisureapp.notifications.ReminderNotification;

import org.jetbrains.annotations.NotNull;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    Fragment fragment = null;

    AnimatedBottomBar animatedBottomBar;

    private Intent intent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        initLocalNotifications();

        DatabaseManager _databaseManager = new DatabaseManager(this);
        _databaseManager.clearTmp();

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
                showFragment();
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {
            }
        });
    }

    public void showFragment() {
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            Log.e(TAG, "Error in creating Fragment");
        }
    }

    @Override
    public void onDestroy() {
        startNotifications();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        startNotifications();
        super.onPause();
    }

    @Override
    public void onStop() {
        startNotifications();
        super.onStop();
    }

    @Override
    public void onResume() {
        cancelNotifications();
        super.onResume();
    }

    private void initLocalNotifications() {
        intent = new Intent(MainActivity.this, ReminderNotification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void startNotifications() {
        if(pendingIntent != null) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long now = System.currentTimeMillis();
            long afterTimeInMillis = 1000 * 2;
            assert alarmManager != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP, now + afterTimeInMillis, pendingIntent);
        }
    }

    private void cancelNotifications() {
        if(pendingIntent != null) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            assert alarmManager != null;
            alarmManager.cancel(pendingIntent);
        }
    }

    private void createNotificationChannel() {
        CharSequence name = getResources().getString(R.string.notification_default);
        String description = getResources().getString(R.string.notification_default);
        int priority = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("daily_reminder", name, priority);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onBackPressed() {
        fragment = new HomeFragment();
        showFragment();
        animatedBottomBar.selectTabById(R.id.tab_home, true);
    }
}