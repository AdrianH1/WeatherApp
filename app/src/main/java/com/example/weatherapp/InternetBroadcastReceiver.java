package com.example.weatherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import androidx.core.content.ContextCompat;

/**
 * Broadcast Reciever zum Erkennen von Verbindungsänderungen
 */

public class InternetBroadcastReceiver extends BroadcastReceiver {

    MainActivity ma;

    public InternetBroadcastReceiver(MainActivity maContext){
        ma = maContext;
    }

    public void onReceive(Context context, Intent intent) {
        try {
            final ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isConnected() || mobile.isConnected()) {
                if (!ma.firstStart)
                {
                    ma.startService();

                    Intent serviceIntent = new Intent(ma, ForegroundService.class);
                    serviceIntent.putExtra("inputExtra", "Verbindung wieder hergestellt");
                    ContextCompat.startForegroundService(ma, serviceIntent);
                }
            } else {
                ma.stopService();

                Intent serviceIntent = new Intent(ma, ForegroundService.class);
                serviceIntent.putExtra("inputExtra", "Verbindung verloren");
                ContextCompat.startForegroundService(ma, serviceIntent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
