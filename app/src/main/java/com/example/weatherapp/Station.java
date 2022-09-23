package com.example.weatherapp;

import java.sql.Timestamp;

/**
 * Klasse zum Umwandlen von Json zu Station Objekt
 */
public class Station {
    private String station;
    private Timestamp timestamp;
    private Temperature values;

    public String getStation() {
        return station;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Temperature getValues() {
        return values;
    }
}




