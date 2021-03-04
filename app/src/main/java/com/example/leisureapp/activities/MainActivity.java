package com.example.leisureapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.leisureapp.FavoritesFragment;
import com.example.leisureapp.ReminderNotification;
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
        // wird für local notifications benötigt
        createNotificationChannel();
        initLocalNotifications();

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

    //
    // Methoden für Notifications
    //

    private Intent intent;
    private PendingIntent pendingIntent;

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
            long oneDayInMillis = 1000*60*60*24;
            // wiederholen alle 24h
            // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now + afterTimeInMillis, oneDayInMillis, pendingIntent);

            // Ohne Wiederholung für Präsentation
            alarmManager.set(AlarmManager.RTC_WAKEUP, now + afterTimeInMillis, pendingIntent);
        }
    }

    private void cancelNotifications() {
        if(pendingIntent != null) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }

    private void createNotificationChannel() {
        // erst ab Android 8 verfügbar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification";
            String description = "Notification";
            int prio = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("daily_reminder", name, prio);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}