package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    private ApiService apiService;
    private TextView txt_state;
    private EditText edt_diff;
    protected boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_diff = (EditText) findViewById(R.id.edt_difference);
        txt_state = (TextView) findViewById(R.id.txt_state);
        Button btn_start = (Button) findViewById(R.id.btn_start);
        Button btn_stop = (Button) findViewById(R.id.btn_stop);

        txt_state.setText("Service gestoppt");

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startService();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService();
            }
        });

        //BroadcastReciever für die Erkennung der Internetverbindung
        BroadcastReceiver br = new InternetBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(br, filter);

        // Api Service starten
        Intent intent = new Intent(this, ApiService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);

        firstStart = false;
    }

    /**
     * Startet Service für API Abfrage der Wetter Daten
     */
    public void startService() {
        float difference = Float.parseFloat(edt_diff.getText().toString());
        txt_state.setText("Service gestartet");
        apiService.running = true;
        apiService.getData(difference);
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Weather Service running");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    /**
     * Stoppt Service für API Abfrage der Wetter Daten
     */
    public void stopService() {
        apiService.running = false;
        txt_state.setText("Service gestoppt");
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }

    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            apiService = ((MyBinder) binder).getService();
        }
        public void onServiceDisconnected(ComponentName className) {
            apiService = null;
        }
    };

}