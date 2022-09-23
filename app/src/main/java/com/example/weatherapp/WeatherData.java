package com.example.weatherapp;

import java.util.ArrayList;

/**
 * Klasse zum Umwandlen von Json zu Objekt
 */
public class WeatherData {
    private boolean ok;
    private float total_count;
    private float row_count;
    ArrayList<Station> result;

    public ArrayList<Station> getResult () {
        return result;
    }
}

