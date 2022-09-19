package com.example.weatherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class InternetBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            if (action == ConnectivityManager.EXTRA_NO_CONNECTIVITY) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
