package com.example.weatherapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class TempNotification extends NotificationCompat{

    NotificationCompat.Builder builder;

/*    private void createNotificationText(String notificationContent) {
        builder = new NotificationCompat.Builder(this, "YOLO")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Wetterkanal")
                .setContentText(notificationContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Wetter";
            String description = "Wetterabfrage";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("asdf", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/
}
