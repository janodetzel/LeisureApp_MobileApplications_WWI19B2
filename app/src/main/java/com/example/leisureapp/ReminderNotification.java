package com.example.leisureapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.leisureapp.R;
import com.example.leisureapp.activities.MainActivity;

public class ReminderNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "daily_reminder")
                .setSmallIcon(R.drawable.leisure_logo_foreground)
                .setContentTitle("Hast du was zu tun?")
                .setContentText("Wenn nicht, hat Leisure neue Aktivitäten.")
                .setContentIntent(MainActivity.pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(213, builder.build());
    }
}
