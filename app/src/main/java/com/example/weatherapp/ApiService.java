package com.example.weatherapp;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Klasse zum Abfragen der API und berechnen der Temperaturdifferenz
 */
public class ApiService extends Service {
    public ApiService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(this);
    }

    /**
     * Holt Temperaturdaten von der API und prüft die Resultate
     * @param difference Differenz, die der User als Grenzwert festgelegt hat
     */
    public void getData (float difference) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    // Da API keine aktuellen Daten liefern, Einträge aus dem letzten Jahr holen. Da nicht jeden Tag Daten vorhanden sind, werden immer die aktuellsten 2 Werte innerhalb des letzten Monats vom letzten Jahr geholt
                    Calendar cal = Calendar.getInstance();
                    Date today = cal.getTime();
                    cal.add(Calendar.YEAR, -1);
                    Date lastYear = cal.getTime();
                    cal.add(Calendar.MONTH, -1);
                    Date lastMonth = cal.getTime();

                    String pattern = "YYYY-MM-dd";
                    SimpleDateFormat simpleDateFormat = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        simpleDateFormat = new SimpleDateFormat(pattern);
                    }

                    String dateFrom = simpleDateFormat.format(lastMonth);
                    String dateTo = simpleDateFormat.format(lastYear);

                    // Daten von API holen
                    URL url = new URL("https://tecdottir.herokuapp.com/measurements/tiefenbrunnen?startDate=" + dateFrom + "&endDate=" + dateTo + "&sort=timestamp_cet%20desc&limit=2&offset=0");
                    URLConnection request = url.openConnection();
                    request.connect();

                    InputStreamReader reader = new InputStreamReader((InputStream)request.getContent());

                    Gson gson = new Gson();
                    WeatherData data = gson.fromJson(reader, WeatherData.class);

                    // Prüfen ob sich die Temperatur um mehr als die Differenz verändert hat
                    if (data.getResult().size() >= 2) {
                        float temp1 = data.getResult().get(0).getValues().getAir_temperature().getValue();
                        float temp2 = data.getResult().get(1).getValues().getAir_temperature().getValue();
                        if (calcDifferenz(temp1, temp2, difference)) {
                            createNotifikation(difference);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        t.start();
    }

    /**
     * Berechnet die Differenz zwischen temp1 und temp2 und prüft, ob difference grösser ist oder nicht
     * @param temp1 Erste Temperatur
     * @param temp2 Zweite Temperatur
     * @param difference Vom User festgelegte Differenz
     * @return true, wenn berechnete Differenz grösser, ansonsten false
     */
    public boolean calcDifferenz(float temp1, float temp2, float difference) {
        float diff = Math.abs(temp1 - temp2);

        if (diff > difference) {
            return true;
        }
        return false;
    }

    /**
     * Gibt eine Meldung aus, dass der Temperaturunterschried grösser ist
     * @param difference Vom User eingegebene Differenz
     */
    public void createNotifikation(float difference) {
        Intent serviceIntent = new Intent(getApplicationContext(), ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Temperatur hat sich um mehr als " + difference + " Grad verändert!");
        ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);
    }
}
