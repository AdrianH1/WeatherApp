package com.example.weatherapp;

/**
 * Klasse zum Umwandlen von Json zu TemperaturData Objekt
 */
public class TemperatureData {
    private float value;
    private String unit;
    private String status;

    public float getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getStatus() {
        return status;
    }
}
